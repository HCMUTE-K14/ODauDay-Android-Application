package com.odauday.data.remote.property;

import static com.odauday.api.EndPoint.PROPERTY;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.MyProperty;
import com.odauday.model.Property;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 4/18/2018.
 */

public interface PropertyService {
    
    @GET(PROPERTY + "/user")
    Single<JsonResponse<List<Property>>> getPropertyOfUser(@Query("user_id") String user_id);
    
    @DELETE(PROPERTY)
    Single<JsonResponse<MessageResponse>> deleteProperty(@Query("id") String property_id);
    
    @POST(PROPERTY)
    Single<JsonResponse<MessageResponse>> create(@Body MyProperty property);
}
