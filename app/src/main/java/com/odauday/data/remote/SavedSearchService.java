package com.odauday.data.remote;

import static com.odauday.api.EndPoint.SAVE_SEARCH;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SavedSearchResponse;
import com.odauday.model.Search;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 4/9/2018.
 */

public interface SavedSearchService {
    
    @GET(SAVE_SEARCH)
    Single<JsonResponse<SavedSearchResponse>> getSearchByUser(@Query("id") String user_id);
    
    @POST(SAVE_SEARCH)
    Single<JsonResponse<MessageResponse>> saveSearch(@Body Search request);
    
    @DELETE(SAVE_SEARCH)
    Single<JsonResponse<MessageResponse>> removeSearch(@Query("id") String search_id);
}
