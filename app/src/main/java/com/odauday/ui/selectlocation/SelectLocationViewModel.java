package com.odauday.ui.selectlocation;

import com.google.android.gms.maps.model.LatLng;
import com.odauday.data.GeoInfoRepository;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/25/18.
 */
public class SelectLocationViewModel extends BaseViewModel {
    
    private final GeoInfoRepository mGeoInfoRepository;
    public static final String TASK_SEARCH_GEO_INFO = "search_geo_info";
    
    @Inject
    public SelectLocationViewModel(GeoInfoRepository geoInfoRepository) {
        this.mGeoInfoRepository = geoInfoRepository;
    }
    
    public void search(GeoLocation location) {
        Disposable disposable = mGeoInfoRepository
                  .search(location)
                  .doOnSubscribe(onSubscribe -> response.setValue(Resource.loading("")))
                  .subscribe(success -> {
                      response.setValue(Resource.success(TASK_SEARCH_GEO_INFO,
                                new AddressAndLocationObject(success, location.toLatLng())));
                  }, error -> {
                      response.setValue(Resource.error(TASK_SEARCH_GEO_INFO, error));
                  });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void search(LatLng latLng) {
        search(GeoLocation.fromLatLng(latLng));
    }
}
