package com.odauday.data.remote.geoinfo;

import static com.odauday.api.EndPoint.GEO_INFO;

import com.odauday.data.remote.model.JsonResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 4/25/18.
 */
public interface GeoInfoService {
    
    @GET(GEO_INFO)
    Single<JsonResponse<String>> search(@Query("lat_lng") String latLng);
}
