package com.odauday.data.remote;

import static com.odauday.api.EndPoint.HISTORY;

import com.odauday.data.remote.history.HistoryDetailResultEntry;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.History;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 4/27/2018.
 */

public interface HistoryService {
    
    @DELETE(HISTORY)
    Single<JsonResponse<MessageResponse>> clearHistory(@Query("user_id") String userId);
    
    
    @POST(HISTORY)
    Single<JsonResponse<MessageResponse>> create(@Body History history);
    
    
    Single<JsonResponse<MessageResponse>> delete(@Body History history);
    
    
    @GET(HISTORY + "/details")
    Single<JsonResponse<HistoryDetailResultEntry>> getDetails(@Query("page") int page,
        @Query("limit") int limit);
}
