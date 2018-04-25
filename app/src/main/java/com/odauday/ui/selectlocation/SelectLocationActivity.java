package com.odauday.ui.selectlocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odauday.R;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.databinding.ActivitySelectLocationBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.utils.MapUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/25/18.
 */
@SuppressLint("MissingPermission")
public class SelectLocationActivity extends
                                    BaseMVVMActivity<ActivitySelectLocationBinding> implements
                                                                                    OnMapReadyCallback,
                                                                                    OnMapClickListener,
                                                                                    OnCameraIdleListener,
                                                                                    OnMyLocationClickListener,
                                                                                    OnMarkerClickListener,
                                                                                    SelectLocationContract {
    
    public static final String TAG = SelectLocationActivity.class.getSimpleName();
    public static final String EXTRA_ADDRESS_AND_LOCATION = "extra_address_and_location";
    public static final String EXTRA_LAST_LOCATION = "extra_last_location";
    
    private GoogleMap mMap;
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    @Inject
    SelectLocationViewModel mSelectLocationViewModel;
    
    private AddressAndLocationObject mAddressAndLocationObject;
    
    private AddressAndLocationObject mLastLocation;
    
    private final PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            mMap.setMyLocationEnabled(true);
        }
        
        @Override
        public void onPermissionDenied() {
        
        }
    };
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mLastLocation = getIntent().getParcelableExtra(EXTRA_LAST_LOCATION);
        }
        initToolBar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.map);
        if (!MapUtils.isHasLocationPermission(this)) {
            MapUtils.requireLocationPermission(this, mPermissionCallBack);
        }
        mapFragment.getMapAsync(this);
    }
    
    private void setTitleToolBar(String text) {
        mBinding.includeToolbar.title.setText(text);
    }
    
    private void initToolBar() {
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        setTitleToolBar(getString(R.string.txt_select_location));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        mBinding.includeToolbar.btnDone.setVisibility(View.VISIBLE);
        mBinding.includeToolbar.btnDone.setOnClickListener(done -> {
            if (mAddressAndLocationObject != null) {
                Intent intent = new Intent();
                Timber.d(mAddressAndLocationObject.getLocation().toString());
                intent.putExtra(EXTRA_ADDRESS_AND_LOCATION, mAddressAndLocationObject);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
              @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mSelectLocationViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(SelectLocationViewModel.TASK_SEARCH_GEO_INFO)) {
                            onErrorGetInfoLocation((Exception) resource.data);
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(SelectLocationViewModel.TASK_SEARCH_GEO_INFO)) {
                            onSuccessGetInfoLocation((AddressAndLocationObject) resource.data);
                        }
                        break;
                    case LOADING:
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_location;
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            
            mMap.setMinZoomPreference(MapViewFragment.MIN_ZOOM_LEVEL);
            mMap.setOnMapClickListener(this);
            mMap.setOnMarkerClickListener(this);
            mMap.setOnCameraIdleListener(this);
            mMap.setOnMyLocationClickListener(this);
            
            if (MapUtils.isHasLocationPermission(this)) {
                mMap.setMyLocationEnabled(true);
            }
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            if (mLastLocation == null) {
                moveToMyLocation();
            } else {
                MarkerOptions markerOptions = createMakerOptionAtLocation(
                          mLastLocation.getLocation())
                          .title(mLastLocation.getAddress());
                mMap.addMarker(markerOptions).showInfoWindow();
                MapUtils.moveMap(mMap, mLastLocation.getLocation(),
                          MapPreferenceHelper.DEFAULT_ZOOM_LEVEL, true);
            }
        }
    }
    
    private void moveToMyLocation() {
        
        MapUtils.moveToMyLocation(this,
                  location -> {
                      MapUtils.moveMap(mMap, new LatLng(location.getLatitude(),
                                          location.getLongitude()),
                                MapPreferenceHelper.DEFAULT_ZOOM_LEVEL);
                  },
                  e -> {
                      Toast.makeText(this,
                                R.string.message_cannot_find_your_location,
                                Toast.LENGTH_SHORT)
                                .show();
                  });
    }
    
    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        search(latLng);
    }
    
    
    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }
    
    @Override
    public void onCameraIdle() {
        // mMap.clear();
    }
    
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        mMap.clear();
        LatLng thatMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
        search(thatMyLocation);
    }
    
    @Override
    public void onSuccessGetInfoLocation(AddressAndLocationObject object) {
        MarkerOptions markerOptions = createMakerOptionAtLocation(object.getLocation());
        markerOptions.title(object.getAddress());
        Marker marker = mMap.addMarker(markerOptions);
        mAddressAndLocationObject = object;
        marker.showInfoWindow();
    }
    
    private MarkerOptions createMakerOptionAtLocation(LatLng latLng) {
        return new MarkerOptions()
                  .position(latLng)
                  .icon(MapUtils.buildMarkSelectedBitmapDescriptor(this));
    }
    
    @Override
    public void onErrorGetInfoLocation(Exception ex) {
    
    }
    
    private void search(LatLng location) {
        mSelectLocationViewModel.search(location);
    }
}
