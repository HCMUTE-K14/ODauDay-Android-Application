package com.odauday.ui.search.mapview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.odauday.R;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.search.model.CoreSearchRequest;
import com.odauday.data.remote.search.model.Location;
import com.odauday.di.Injectable;
import com.odauday.ui.search.mapview.MapOverlayView.MapOverlayListener;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/10/18.
 */

@SuppressLint("MissingPermission")
public class MapViewFragment extends SupportMapFragment implements OnMapReadyCallback,
                                                                   OnCameraIdleListener,
                                                                   MapOverlayListener,
                                                                   ConnectionCallbacks,
                                                                   OnConnectionFailedListener,
                                                                   Injectable {
    
    public static final String TAG = MapViewFragment.class.getSimpleName();
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    private GoogleMap mMap;
    
    private GoogleApiClient mLocationClient;
    
    private Location mLastLocation;
    private float mZoomLevel;
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            mMap.setMyLocationEnabled(true);
        }
        
        @Override
        public void onPermissionDenied() {
        
        }
    };
    
    
    public static MapViewFragment newInstance() {
        
        Bundle args = new Bundle();
        Timber.tag(TAG).d("New instance MapViewFragment");
        
        MapViewFragment fragment = new MapViewFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            injectDI();
        }
        super.onAttach(activity);
    }
    
    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            injectDI();
        }
        super.onAttach(context);
    }
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mLastLocation = mMapPreferenceHelper.getLastLocation();
        mZoomLevel = mMapPreferenceHelper.getLastZoomLevel();
        if (getActivity() != null) {
            this.mLocationClient = new Builder(getActivity())
                      .addApi(LocationServices.API)
                      .addConnectionCallbacks(this)
                      .addOnConnectionFailedListener(this)
                      .build();
        }
        
        this.getMapAsync(this);
    }
    
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getActivity() == null) {
            return null;
        }
        ViewGroup mapView = new FrameLayout(getActivity());
        mapView.addView(super.onCreateView(layoutInflater, viewGroup, bundle));
        MapOverlayView mapOverlay = new MapOverlayView((AppCompatActivity) getActivity());
        mapOverlay.setMapOverlayListener(this);
        mapView.addView(mapOverlay);
        
        return mapView;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
              @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    public void onStart() {
        super.onStart();
        this.mLocationClient.connect();
    }
    
    public void onStop() {
        super.onStop();
        this.mLocationClient.disconnect();
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }
        this.mMap = googleMap;
        
        MapUtils.moveMap(mMap, mLastLocation.toLatLng(), mZoomLevel, false);
        mMap.setOnCameraIdleListener(this);
        
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
    }
    
    @Override
    public void onCameraIdle() {
        mMap.clear();
        
        MapUtils.drawBound(mMap);
        int ot = getResources().getConfiguration().orientation;
        CoreSearchRequest coreSearchRequest = MapUtils.getCoreSearchRequest(mMap, ot);
        
        MapUtils.drawCircle(mMap,
                  coreSearchRequest.getCenter().toLatLng(),
                  coreSearchRequest.getRadius());
    }
    
    @Override
    public void onClickMyLocation() {
        if (MapUtils.isHasLocationPermission(getActivity())) {
            if (this.mMap != null) {
                this.mMap.setMyLocationEnabled(true);
                if (this.mLocationClient == null || !this.mLocationClient.isConnected()) {
                    if (mLocationClient != null) {
                        this.mLocationClient.connect();
                        return;
                    }
                }
                moveToMyLocation();
            }
        } else {
            MapUtils.requireLocationPermission(getActivity(), mPermissionCallBack);
        }
    }
    
    private void moveToMyLocation() {
        if (getActivity() == null) {
            return;
        }
        LocationServices
                  .getFusedLocationProviderClient(getActivity())
                  .getLastLocation()
                  .addOnSuccessListener(this.getActivity(),
                            location -> {
                                if (location == null) {
                                    showEnableGps();
                                } else {
                                    MapUtils.moveMap(mMap, new LatLng(location.getLatitude(),
                                                        location.getLongitude()),
                                              MapPreferenceHelper.DEFAULT_ZOOM_LEVEL);
                                }
                            })
                  .addOnFailureListener(this.getActivity(), e -> {
                      Toast.makeText(getActivity(), R.string.message_cannot_find_your_location,
                                Toast.LENGTH_SHORT)
                                .show();
                  });
    }
    
    private void showEnableGps() {
        
        if (NetworkUtils.isEnableGPS(getActivity())) {
            ViewUtils.showGoToSettingsDialog((AppCompatActivity) getActivity());
        } else {
            Toast.makeText(getActivity(), R.string.message_waiting_for_location,
                      Toast.LENGTH_SHORT)
                      .show();
        }
    }
    
    @Override
    public void onClickMapLayer(int type) {
        if (type == 0) {
            this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (type == 1) {
            this.mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            this.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }
    
    @Override
    public void onMapLockToggle(boolean isMapUnLocked) {
    }
    
    
    protected void injectDI() {
        AndroidSupportInjection.inject(this);
    }
    
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    
    }
    
    @Override
    public void onConnectionSuspended(int i) {
    
    }
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d(connectionResult.getErrorMessage());
    }
}
