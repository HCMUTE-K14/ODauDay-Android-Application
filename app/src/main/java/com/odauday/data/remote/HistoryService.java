package com.odauday.data.remote;

import static com.odauday.api.EndPoint.HISTORY;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 4/27/2018.
 */

public interface HistoryService {
    
    @DELETE(HISTORY)
    Single<JsonResponse<MessageResponse>> clearHistory(@Query("user_id") String userId);
}
