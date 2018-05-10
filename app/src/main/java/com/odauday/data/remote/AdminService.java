package com.odauday.data.remote;

import static com.odauday.api.EndPoint.ADMIN;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.model.Property;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 5/4/2018.
 */

public interface AdminService {
    
    @GET(ADMIN+"/property")
    Single<JsonResponse<List<Property>>> getPropertyByAdmin(@Query("page") String page,
        @Query("limit") String limit, @Query("status") String status, @Query("likename") String likeName);
}
