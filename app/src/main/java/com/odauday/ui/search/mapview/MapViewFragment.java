package com.odauday.ui.search.mapview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odauday.R;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.SearchPropertyState;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.data.remote.property.model.CoreSearchRequest;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.di.Injectable;
import com.odauday.ui.search.common.RxCameraIdleListener;
import com.odauday.ui.search.common.RxCameraIdleListener.TriggerCameraIdle;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.event.NeedCloseVitalPropertyEvent;
import com.odauday.ui.search.common.event.OnCompleteDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnFavouriteEvent;
import com.odauday.ui.search.common.event.OnSelectedPlaceEvent;
import com.odauday.ui.search.common.event.OnUpdateCriteriaEvent;
import com.odauday.ui.search.common.event.ReloadSearchEvent;
import com.odauday.ui.search.mapview.MapOverlayView.MapOverlayListener;
import com.odauday.ui.search.mapview.MapViewAdapter.OnUpdatedListLocation;
import com.odauday.utils.MapUtils;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import dagger.android.support.AndroidSupportInjection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
                                                                   TriggerCameraIdle,
                                                                   OnMarkerClickListener,
                                                                   OnMapClickListener,
                                                                   MapOverlayListener,
                                                                   ConnectionCallbacks,
                                                                   OnConnectionFailedListener,
                                                                   OnUpdatedListLocation,
                                                                   Injectable {
    
    public static final String TAG = MapViewFragment.class.getSimpleName();
    
    public static final float MIN_ZOOM_LEVEL = 5.45f;
    
    private static final int DEBOUNCE_TIME = 500;
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    @Inject
    SearchPropertyRepository mSearchRepository;
    
    @Inject
    EventBus mBus;
    
    @Inject
    MapViewAdapter mMapViewAdapter;
    
    
    private GoogleMap mMap;
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
    
    private GoogleApiClient mLocationClient;
    private MapOverlayView mMapOverlayView;
    private MapFragmentClickCallBack mMapFragmentClickCallBack;
    private GeoLocation mLastGeoLocation;
    private GeoLocation[] mLastBounds;
    private HashMap<GeoLocation, Marker> mMapMarkers = new HashMap<>();
    private Collection<GeoLocation> mPendingToShowLocations;
    private float mZoomLevel;
    private RxCameraIdleListener mRxCameraIdleListener;
    private boolean mIsSearchWithSuggestionLocation;
    private AutoCompletePlace mAutoCompletePlace;
    private boolean mMapUnlocked;
    private Marker mOpenedMarker;
    private GeoLocation mOpenedLocation;
    
    private boolean mIsShowVitalProperty;
    private CameraPosition mLastCameraPosition;
    private boolean mDistanceTooShort;
    
    
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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mBus.register(this);
        mLastGeoLocation = mMapPreferenceHelper.getLastLocation();
        mZoomLevel = mMapPreferenceHelper.getLastZoomLevel();
        mLastBounds = mMapPreferenceHelper.getLastBounds();
        
        mMapUnlocked = mMapPreferenceHelper.getIsMapUnlocked();
        if (getActivity() != null) {
            this.mLocationClient = new Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        }
        
        mMapViewAdapter.setOnUpdatedListLocation(this);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getActivity() == null) {
            return null;
        }
        ViewGroup mapView = new MapViewContainer(getActivity());
        mapView.addView(super.onCreateView(layoutInflater, viewGroup, bundle));
        mMapOverlayView = new MapOverlayView(getActivity());
        mMapOverlayView.setMapOverlayListener(this);
        mapView.addView(mMapOverlayView);
        this.getMapAsync(this);
        mMapOverlayView.getButtonLockMap().setChecked(mMapUnlocked);
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
    }
    
    public void clear() {
        mLocationClient.disconnect();
        mMapViewAdapter.clear();
        mRxCameraIdleListener.stop();
    }
    
    @Override
    public void onStop() {
        clear();
        super.onStop();
    }
    
    @Override
    public void onDestroy() {
        mBus.unregister(this);
        mPermissionCallBack = null;
        clear();
        mLocationClient = null;
        mRxCameraIdleListener = null;
        mMap = null;
        mMapViewAdapter.destroy();
        mMapViewAdapter = null;
        mMapFragmentClickCallBack = null;
        
        Timber.d("on destroy map view");
        super.onDestroy();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapOverlayView = null;
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }
        this.mMap = googleMap;
        mRxCameraIdleListener = new RxCameraIdleListener(mMap, this);
        
        mMap.setMinZoomPreference(MIN_ZOOM_LEVEL);
        
        mRxCameraIdleListener.start();
        
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        
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
        checkIsSearchWithSuggestion();
        if (!isDetached() && !isHidden() && isAdded()) {
            boolean insignificantMove;
            CameraPosition currentCameraPosition = mMap.getCameraPosition();
            insignificantMove =
                (MapUtils.isCamPositionEqual(currentCameraPosition,
                    this.mLastCameraPosition) || this.mDistanceTooShort)
                &&
                MapUtils.isCamPositionEqual(currentCameraPosition,
                    this.mLastCameraPosition);
            if (!insignificantMove) {
                //                    if (this.mMapUnlocked && !this.myIgnoreInitCameraChange) {
                //                        closeOpenedMarker();
                //                        onValidUserTriggeredMapMove();
                //                    }
                this.mLastCameraPosition = currentCameraPosition;
                if (mMapUnlocked) {
                    performSearch();
                }
            }
        }
    }
    
    @Override
    public void onMapClick(LatLng latLng) {
        closeOpenedMarker();
        closeVitals();
        checkIsSearchWithSuggestion();
    }
    
    public void checkIsSearchWithSuggestion() {
        if (mAutoCompletePlace != null &&
            MapUtils.isVisibleInBounds(mMap, mAutoCompletePlace.getLocation())) {
            mIsSearchWithSuggestionLocation = true;
        } else {
            mIsSearchWithSuggestionLocation = false;
        }
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
    public void onUpdatedSearchResult(OnCompleteDownloadPropertyEvent completeDownloadProperty) {
        
        mMapViewAdapter.setData(completeDownloadProperty.getResult());
        closeOpenedMarker();
        closeVitals();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void needReloadSearch(ReloadSearchEvent reloadSearchEvent) {
        performSearch();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedSuggestionPlace(OnSelectedPlaceEvent onSelectedPlaceEvent) {
        new Handler().postDelayed(() -> {
            if (!onSelectedPlaceEvent.isNeedMoveMapInMapFragment()) {
                return;
            }
            AutoCompletePlace autoCompletePlace = onSelectedPlaceEvent.getData();
            GeoLocation locationSelectedPlace = autoCompletePlace.getLocation();
            MapUtils.moveMap(mMap, locationSelectedPlace.toLatLng(),
                MapPreferenceHelper.DEFAULT_ZOOM_LEVEL, true);
            mSearchRepository.getCurrentSearchRequest().getCriteria().getDisplay()
                .setDisplayLocation(autoCompletePlace.getName());
            
            mAutoCompletePlace = autoCompletePlace;
            mBus.post(new OnUpdateCriteriaEvent());
            mIsSearchWithSuggestionLocation = true;
        }, 1000);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFavouriteEvent(OnFavouriteEvent event) {
        PropertyResultEntry resultEntry = event.getResult();
        GeoLocation geoLocation = resultEntry.getLocation();
        
        Marker marker = mMapMarkers.get(geoLocation);
        marker.setVisible(false);
        
        marker.setIcon(mMapViewAdapter.getMarkerIconForLocation(geoLocation));
        marker.setVisible(true);
        if (resultEntry.isFavorite()) {
            mMapViewAdapter.addToFavoriteList(resultEntry);
        } else {
            mMapViewAdapter.removeToFavoriteList(resultEntry);
        }
    }
    
    
    @Override
    public void onClickMapLayer(int type) {
        switch (type) {
            case 0:
                this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                this.mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            default:
                this.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
    }
    
    @Override
    public void onMapLockToggle(boolean isMapUnLocked) {
        if (!isMapUnLocked) {
            Toast.makeText(this.getContext(), R.string.txt_map_is_locked,
                Toast.LENGTH_SHORT).show();
        }
        mMapUnlocked = isMapUnLocked;
        mMapPreferenceHelper.putIsMapUnlocked(mMapUnlocked);
        mMapOverlayView.getButtonLockMap().setChecked(isMapUnLocked);
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
        if (!mIsSearchWithSuggestionLocation) {
            mSearchRepository.getCurrentSearchRequest().getCriteria().getDisplay()
                .setDisplayLocation(getString(R.string.txt_map_area));
            mAutoCompletePlace = null;
            mBus.post(new OnUpdateCriteriaEvent());
        }
    }
    
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.mMap == null) {
            return true;
        }
        setSelectedMarker(marker);
        PropertyResultEntry entry = mMapViewAdapter
            .getEntriesAtLocation(mOpenedLocation)
            .get(0);
        entry.setVisited(true);
        
        marker.setVisible(false);
        
        marker.setIcon(mMapViewAdapter.getMarkerIconForLocation(mOpenedLocation));
        marker.setVisible(true);
        
        mMapViewAdapter
            .addToHistoryList(entry);
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
        this.mBus.post(new NeedCloseVitalPropertyEvent());
    }
    
    private void setSelectedMarker(Marker marker) {
        boolean isSameMarker;
        isSameMarker = this.mOpenedMarker != null &&
                       marker.getPosition().equals(this.mOpenedMarker.getPosition());
        if (!isSameMarker) {
            List<PropertyResultEntry> entry = mMapViewAdapter
                .getEntriesAtLocation(marker.getPosition());
            
            mMapFragmentClickCallBack.onMapPropertyClick(entry.get(0));
            mIsShowVitalProperty = true;
            
            closeOpenedMarker();
        } else {
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
    
    public void moveToMyLocation() {
        MapUtils.moveToMyLocation(getActivity(), location -> {
            if (location == null) {
                showEnableGps();
            } else {
                MapUtils.moveMap(mMap, new LatLng(location.getLatitude(),
                        location.getLongitude()),
                    MapPreferenceHelper.DEFAULT_ZOOM_LEVEL);
            }
        }, e -> Toast.makeText(getActivity(),
            R.string.message_cannot_find_your_location,
            Toast.LENGTH_SHORT)
            .show());
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
        MapUtils.drawCircle(mMap, searchRequest.getCore().getCenter().toLatLng(),
            searchRequest.getCore().getRadius());
        saveStateSearch(searchRequest);
    }
    
    private void saveStateSearch(SearchRequest searchRequest) {
        mSearchRepository.setCurrentSearchRequest(searchRequest);
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
    
    public MapViewAdapter getMapViewAdapter() {
        return mMapViewAdapter;
    }
    
    public interface MapFragmentClickCallBack {
        
        void onMapPropertyClick(PropertyResultEntry entry);
        
        //void on
    }
    
    
    private class MapViewContainer extends FrameLayout {
        
        float mFinalPosX = 0.0f;
        float mFinalPosY = 0.0f;
        float mInitPosX = 0.0f;
        float mInitPosY = 0.0f;
        
        public MapViewContainer(Context context) {
            super(context);
        }
        
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            if (MapViewFragment.this.mMap == null) {
                return false;
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.mInitPosX = ev.getX();
                    this.mInitPosY = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    this.mFinalPosX = ev.getX();
                    this.mFinalPosY = ev.getY();
                    if (Math.abs(this.mFinalPosX - this.mInitPosX) <= 50.0f &&
                        Math.abs(this.mFinalPosY - this.mInitPosY) <= 50.0f) {
                        MapViewFragment.this.mDistanceTooShort = true;
                        break;
                    }
                    MapViewFragment.this.mDistanceTooShort = false;
                    break;
                default:
                    break;
            }
            return super.onInterceptTouchEvent(ev);
        }
    }
}
