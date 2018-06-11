package com.odauday.data.remote.premium;

import static com.odauday.api.EndPoint.PREMIUM;
import static com.odauday.api.EndPoint.SUBSCRIBE;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.premium.model.SubscribeRequest;
import com.odauday.model.Premium;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by infamouSs on 5/31/18.
 */
public interface PremiumService {
    
    @GET(PREMIUM)
    Single<JsonResponse<List<Premium>>> get();
    
    @POST(SUBSCRIBE)
    Single<JsonResponse<MessageResponse>> subscribe(@Body SubscribeRequest request);
}
