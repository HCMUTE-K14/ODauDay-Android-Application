package com.odauday.data.remote;

import static com.odauday.api.EndPoint.ADMIN;

import com.odauday.data.remote.history.HistoryDetailResultEntry;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.Property;
import com.odauday.model.User;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 5/4/2018.
 */

public interface AdminService {
    
    @GET(ADMIN+"/property")
    Single<JsonResponse<List<Property>>> getPropertyByAdmin(@Query("page") String page,
            @Query("limit") String limit, @Query("status") String status, @Query("likename") String likeName);
    @GET(ADMIN+"/user")
    Single<JsonResponse<List<User>>> getUserByAdmin(@Query("page") String page,
        @Query("limit") String limit, @Query("status") String status, @Query("likename") String likeName);
    @PUT(ADMIN + "/change-status-user")
    Single<JsonResponse<MessageResponse>> changeStatusUser(@Query("id") String id,@Query("status")String status);
    @PUT(ADMIN+"/change-status-property")
    Single<JsonResponse<MessageResponse>> changeStatusProperty(@Query("id") String property_id,@Query("status") String status);
    @GET(ADMIN+"/user-history")
    Single<JsonResponse<HistoryDetailResultEntry>> getListHistoryForUser(@Query("user_id") String user_id,@Query("page")String page,@Query("limit")String limit);
}
