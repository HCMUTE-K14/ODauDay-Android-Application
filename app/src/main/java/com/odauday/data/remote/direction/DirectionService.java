package com.odauday.data.remote.direction;

import static com.odauday.api.EndPoint.DIRECTION;

import com.odauday.data.remote.direction.model.DirectionResponse;
import com.odauday.data.remote.model.JsonResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 5/12/18.
 */
public interface DirectionService {
    
    
    @GET(DIRECTION)
    Single<JsonResponse<DirectionResponse>> get(@Query("from") String from, @Query("to") String to,
        @Query("mode") String mode);
}
