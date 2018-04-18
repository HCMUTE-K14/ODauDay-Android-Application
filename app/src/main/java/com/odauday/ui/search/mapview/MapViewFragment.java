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
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odauday.R;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.SearchPropertyState;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.property.model.CoreSearchRequest;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.di.Injectable;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.event.NeedCloseVitalProperty;
import com.odauday.ui.search.common.event.OnCompleteDownloadProperty;
import com.odauday.ui.search.common.event.ReloadSearchEvent;
import com.odauday.ui.search.mapview.MapOverlayView.MapOverlayListener;
import com.odauday.ui.search.mapview.MapViewAdapter.OnUpdatedListLocation;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import dagger.android.support.AndroidSupportInjection;
import java.util.Collection;
import java.util.HashMap;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/10/18.
 */

@SuppressLint("MissingPermission")
public class MapViewFragment extends SupportMapFragment implements OnMapReadyCallback,
                                                                   OnCameraIdleListener,
                                                                   OnMarkerClickListener,
                                                                   MapOverlayListener,
                                                                   ConnectionCallbacks,
                                                                   OnConnectionFailedListener,
                                                                   OnUpdatedListLocation,
                                                                   Injectable {
    
    public static final String TAG = MapViewFragment.class.getSimpleName();
    
    private static final float MIN_ZOOM_LEVEL = 5.45f;
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    @Inject
    SearchPropertyRepository mSearchRepository;
    
    @Inject
    EventBus mBus;
    
    private GoogleMap mMap;
    
    private GoogleApiClient mLocationClient;
    
    
    private MapFragmentClickCallBack mMapFragmentClickCallBack;
    
    private GeoLocation mLastGeoLocation;
    private GeoLocation[] mLastBounds;
    
    private HashMap<GeoLocation, Marker> mMapMarkers = new HashMap<>();
    private Collection<GeoLocation> mPendingToShowLocations;
    private MapViewAdapter mMapViewAdapter;
    
    private float mZoomLevel;
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            mMap.setMyLocationEnabled(true);
            moveToMyLocation();
        }
        
        @Override
        public void onPermissionDenied() {

        }
    };
    
    private Marker mOpenedMarker;
    private GeoLocation mOpenedLocation;
    
    private boolean mIsShowVitalProperty;
    
    public static MapViewFragment newInstance() {

        Bundle args = new Bundle();
        Timber.tag(TAG).d("New instance MapViewFragment");

        MapViewFragment fragment = new MapViewFragment();
        fragment.setArguments(args);

        return fragment;
    }
    
    public boolean isShowVitalProperty() {
        return mIsShowVitalProperty;
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
    
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (nextAnim == 0) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
        
        Animation anim = android.view.animation.AnimationUtils
                  .loadAnimation(getContext(), nextAnim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return anim;
    }
    
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getMapAsync(this);
    }
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        mLastGeoLocation = mMapPreferenceHelper.getLastLocation();
        mZoomLevel = mMapPreferenceHelper.getLastZoomLevel();
        mLastBounds = mMapPreferenceHelper.getLastBounds();
        if (getActivity() != null) {
            this.mLocationClient = new Builder(getActivity())
                      .addApi(LocationServices.API)
                      .addConnectionCallbacks(this)
                      .addOnConnectionFailedListener(this)
                      .build();
        }
        
        mMapViewAdapter = new MapViewAdapter(this.getContext());
        mMapViewAdapter.setOnUpdatedListLocation(this);
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
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.mLocationClient.connect();
        mBus.register(this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.mLocationClient.disconnect();
        mBus.unregister(this);
    }
    
    @Override
    public void onDestroy() {
        mMapViewAdapter = null;
        mLocationClient = null;
        super.onDestroy();
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }
        this.mMap = googleMap;
        mMap.setMinZoomPreference(MIN_ZOOM_LEVEL);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
        
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        
        moveToLastLocation();
        
        if (this.mPendingToShowLocations != null) {
            onUpdatedListLocation(this.mPendingToShowLocations);
            this.mPendingToShowLocations = null;
        }
    }
    
    @Override
    public void onCameraIdle() {
        performSearch();
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
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatedSearchResult(OnCompleteDownloadProperty completeDownloadProperty) {
        
        mMapViewAdapter.setData(completeDownloadProperty.getResult());
        closeOpenedMarker();
        closeVitals();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void needReloadSearch(ReloadSearchEvent reloadSearchEvent) {
        performSearch();
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
    
    @Override
    public void onUpdatedListLocation(Collection<GeoLocation> locations) {
        if (this.mMap == null) {
            this.mPendingToShowLocations = locations;
            return;
        }
        this.mMap.clear();
        this.mMapMarkers.clear();
        for (GeoLocation mapDisplayItem : locations) {
            LatLng entryLatLng = mapDisplayItem.toLatLng();
            boolean isInVisibleRegion = mMap.getProjection().getVisibleRegion().latLngBounds
                      .contains(entryLatLng);
            
            if (!isInVisibleRegion) {
                continue;
            }
            MarkerOptions markerOptions = new MarkerOptions()
                      .position(entryLatLng)
                      .icon(this.mMapViewAdapter.getMarkerIconForLocation(mapDisplayItem));
            
            Marker marker = this.mMap.addMarker(markerOptions);
            this.mMapMarkers.put(mapDisplayItem, marker);
        }
        mBus.post(SearchPropertyState.COMPLETE_SHOW_DATA);
    }
    
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.mMap == null) {
            return true;
        }
        setSelectedMarker(marker);
        
        return true;
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
    
    private void closeOpenedMarker() {
        if (this.mOpenedMarker != null) {
            this.mOpenedMarker.remove();
            this.mOpenedMarker = null;
            this.mOpenedLocation = null;
        }
    }
    
    private void closeVitals() {
        this.mBus.post(new NeedCloseVitalProperty());
    }
    
    private void setSelectedMarker(Marker marker) {
        boolean isSameMarker;
        if (this.mOpenedMarker == null ||
            !marker.getPosition().equals(this.mOpenedMarker.getPosition())) {
            isSameMarker = false;
        } else {
            isSameMarker = true;
        }
        if (!isSameMarker) {
            PropertyResultEntry entry = mMapViewAdapter
                      .getEntriesAtLocation(marker.getPosition())
                      .get(0);
            
            mMapFragmentClickCallBack.onMapPropertyClick(entry);
            mIsShowVitalProperty = true;
            
            closeOpenedMarker();
        }
        setOpenedMarker(marker);
    }
    
    
    private void setOpenedMarker(Marker openedMarker) {
        MarkerOptions options = new MarkerOptions();
        options.icon(mMapViewAdapter.getMarkSelectedBitmapDescriptor());
        options.position(openedMarker.getPosition());
        this.mOpenedMarker = this.mMap.addMarker(options);
        this.mOpenedLocation = GeoLocation.fromLatLng(this.mOpenedMarker.getPosition());
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
        if (getActivity() == null) {
            return;
        }
        if (!NetworkUtils.isEnableGPS(getActivity())) {
            ViewUtils.showGoToSettingsDialog((AppCompatActivity) getActivity());
        } else {
            Toast.makeText(getActivity(), R.string.message_waiting_for_location,
                      Toast.LENGTH_SHORT)
                      .show();
        }
        
    }
    
    private void moveToLastLocation() {
        MapUtils.moveMap(mMap, mLastGeoLocation.toLatLng(), mZoomLevel, false);
        try {
            mMap.moveCamera(CameraUpdateFactory
                      .newLatLngBounds(
                                new LatLngBounds(mLastBounds[0].toLatLng(),
                                          mLastBounds[1].toLatLng()),
                                0));
        } catch (Exception ignored) {

        }
    }
    
    
    private void performSearch() {
        SearchRequest searchRequest = makeSearchRequest();
        mSearchRepository.search(searchRequest);
        
        saveStateSearch(searchRequest);
    }
    
    private void saveStateSearch(SearchRequest searchRequest) {
        mMapPreferenceHelper.putLastLocation(searchRequest.getCore().getCenter());
        mMapPreferenceHelper.putLastZoomLevel(searchRequest.getZoom());
        mMapPreferenceHelper.putLastBounds(searchRequest.getCore().getBounds());
    }
    
    private SearchRequest makeSearchRequest() {
        int ot = getResources().getConfiguration().orientation;
        float zoom = mMap.getCameraPosition().zoom;
        
        CoreSearchRequest coreSearchRequest = MapUtils
                  .getCoreSearchRequestFromCurrentLocation(mMap, ot);
        
        SearchCriteria searchCriteria = mSearchRepository
                  .getCurrentSearchRequest()
                  .getCriteria();
        
        return new SearchRequest(coreSearchRequest, searchCriteria,
                  zoom);
    }
    
    public void setMapFragmentClickCallBack(
              MapFragmentClickCallBack mapFragmentClickCallBack) {
        mMapFragmentClickCallBack = mapFragmentClickCallBack;
    }
    
    
    public interface MapFragmentClickCallBack {
        
        void onMapPropertyClick(PropertyResultEntry entry);
        
        //void on
    }
    
}
