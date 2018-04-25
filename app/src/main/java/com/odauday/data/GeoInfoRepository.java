package com.odauday.data;

import com.google.android.gms.maps.model.LatLng;
import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.geoinfo.GeoInfoService;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.exception.AutCompletePlaceException;
import com.odauday.exception.GeoInfoException;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/25/18.
 */
public class GeoInfoRepository implements Repository {
    
    private final GeoInfoService mGeoInfoService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public GeoInfoRepository(GeoInfoService geoInfoService, SchedulersExecutor schedulersExecutor) {
        this.mGeoInfoService = geoInfoService;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<String> search(GeoLocation lngLng) {
        String query = new StringBuilder().append(lngLng.getLatitude())
                  .append(",").append(lngLng.getLongitude()).toString();
        
        return callApi(query);
    }
    
    
    public Single<String> search(LatLng lngLng) {
        String query = new StringBuilder().append(lngLng.latitude)
                  .append(",").append(lngLng.longitude).toString();
        
        return callApi(query);
    }
    
    private Single<String> callApi(String lngLng) {
        return mGeoInfoService.search(lngLng)
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui())
                  .map(response -> {
                      try {
                          if (response.isSuccess()) {
                              return response.getData();
                          } else {
                              throw new GeoInfoException(response.getErrors());
                          }
                      } catch (Exception ex) {
                          if (ex instanceof AutCompletePlaceException) {
                              throw ex;
                          }
                          throw new GeoInfoException(ex.getMessage());
                      }
                  });
    }
}
