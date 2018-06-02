package com.odauday.ui.selectlocation;

import static com.odauday.config.Constants.Task.TASK_SEARCH_GEO_INFO;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
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
import com.odauday.config.AppConfig;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.databinding.ActivitySelectLocationBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.common.event.OnSelectedPlaceEvent;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.utils.MapUtils;
import com.odauday.utils.TextUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
                                                                                    SelectLocationContract,
                                                                                    ConnectionCallbacks,
                                                                                    OnConnectionFailedListener {
    
    public static final String TAG = SelectLocationActivity.class.getSimpleName();
    public static final String EXTRA_ADDRESS_AND_LOCATION = "extra_address_and_location";
    public static final String EXTRA_LAST_LOCATION = "extra_last_location";
    
    private GoogleMap mMap;
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    @Inject
    SelectLocationViewModel mSelectLocationViewModel;
    
    @Inject
    EventBus mBus;
    
    private Marker mCurrentMarker;
    
    private GoogleApiClient mLocationClient;
    
    private AddressAndLocationObject mAddressAndLocationObject;
    
    private final PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            mMap.setMyLocationEnabled(true);
        }
        
        @Override
        public void onPermissionDenied() {
            Toast.makeText(SelectLocationActivity.this,
                R.string.message_permission_location_request, Toast.LENGTH_SHORT).show();
        }
    };
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus.register(this);
        if (getIntent() != null) {
            mAddressAndLocationObject = getIntent().getParcelableExtra(EXTRA_LAST_LOCATION);
        }
        
        this.mLocationClient = new Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
        initToolBar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        if (!MapUtils.isHasLocationPermission(this)) {
            MapUtils.requireLocationPermission(this, mPermissionCallBack);
        }
        mapFragment.getMapAsync(this);
    }
    
    @Override
    protected void onDestroy() {
        mBus.unregister(this);
        super.onDestroy();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedSuggestionPlace(OnSelectedPlaceEvent event) {
        new Handler().postDelayed(() -> {
            AutoCompletePlace autoCompletePlace = event.getData();
            GeoLocation locationSelectedPlace = autoCompletePlace.getLocation();
            MapUtils.moveMap(mMap, locationSelectedPlace.toLatLng(),
                MapPreferenceHelper.DEFAULT_ZOOM_LEVEL, true);
            
            mAddressAndLocationObject = new AddressAndLocationObject(autoCompletePlace.getName(),
                autoCompletePlace.getLocation().toLatLng());
            
            MarkerOptions markerOptions = createMakerOptionAtLocation(
                mAddressAndLocationObject.getLocation());
            markerOptions.title(TextUtils.formatAddress(mAddressAndLocationObject.getAddress()));
            
            mMap.addMarker(markerOptions).showInfoWindow();
            
        }, 300);
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
        mBinding.includeToolbar.image.setVisibility(View.VISIBLE);
        mBinding.includeToolbar.image.setOnClickListener(this::openAutoCompletePlaceActivity);
        
        mBinding.includeToolbar.btnDone.setVisibility(View.VISIBLE);
        mBinding.includeToolbar.btnDone.setOnClickListener(done -> {
            if (mAddressAndLocationObject != null) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_ADDRESS_AND_LOCATION, mAddressAndLocationObject);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.message_cannot_find_that_location_please_try_later,
                    Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }
    
    public void openAutoCompletePlaceActivity(View view) {
        Intent intent = new Intent(this, AutoCompletePlaceActivity.class);
        intent.putExtra(AutoCompletePlaceActivity.EXTRA_NEED_MOVE_MAP_IN_MAP_FRAGMENT, false);
        
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
            android.R.anim.fade_out);    }
    
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
                        if (resource.task.equals(TASK_SEARCH_GEO_INFO)) {
                            onErrorGetInfoLocation((Exception) resource.data);
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(TASK_SEARCH_GEO_INFO)) {
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
            mLocationClient.connect();
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
            if (mAddressAndLocationObject == null) {
                moveToMyLocation();
            } else {
                MarkerOptions markerOptions = createMakerOptionAtLocation(
                    mAddressAndLocationObject.getLocation())
                    .title(TextUtils.formatAddress(mAddressAndLocationObject.getAddress()));
                mMap.addMarker(markerOptions).showInfoWindow();
                MapUtils.moveMap(mMap, mAddressAndLocationObject.getLocation(),
                    MapPreferenceHelper.DEFAULT_ZOOM_LEVEL, true);
            }
        }
    }
    
    private void moveToMyLocation() {
        
        MapUtils.moveToMyLocation(this,
            location -> {
                if (location != null) {
                    MapUtils.moveMap(mMap, new LatLng(location.getLatitude(),
                            location.getLongitude()),
                        MapPreferenceHelper.DEFAULT_ZOOM_LEVEL);
                }
            },
            e -> {
                MapUtils.moveMap(mMap, AppConfig.DEFAULT_GEO_LOCATION.toLatLng(),
                    MapViewFragment.MIN_ZOOM_LEVEL, true);
                Toast.makeText(this,
                    R.string.message_cannot_find_your_location,
                    Toast.LENGTH_SHORT)
                    .show();
            });
    }
    
    
    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        MarkerOptions markerOptions = createMakerOptionAtLocation(latLng);
        mCurrentMarker = mMap.addMarker(markerOptions);
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
        MarkerOptions markerOptions = createMakerOptionAtLocation(thatMyLocation);
        mCurrentMarker = mMap.addMarker(markerOptions);
        search(thatMyLocation);
    }
    
    @Override
    public void onSuccessGetInfoLocation(AddressAndLocationObject object) {
        mCurrentMarker.setTitle(TextUtils.formatAddress(object.getAddress()));
        mAddressAndLocationObject = object;
        mCurrentMarker.showInfoWindow();
    }
    
    private MarkerOptions createMakerOptionAtLocation(LatLng latLng) {
        return new MarkerOptions()
            .position(latLng)
            .icon(MapUtils.buildMarkSelectedBitmapDescriptorNoPadding(this));
    }
    
    @Override
    public void onErrorGetInfoLocation(Exception ex) {
    }
    
    private void search(LatLng location) {
        mSelectLocationViewModel.search(location);
    }
    
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    
    }
    
    @Override
    public void onConnectionSuspended(int i) {
    
    }
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    
    }
}
