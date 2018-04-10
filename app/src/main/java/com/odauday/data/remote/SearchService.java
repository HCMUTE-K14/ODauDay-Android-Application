package com.odauday.data.remote;

import static com.odauday.api.EndPoint.SEARCH;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SearchResponse;
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

public interface SearchService {
    
    interface Public{
        @GET(SEARCH)
        Single<JsonResponse<SearchResponse>> getSearchByUser(@Query("id")String user_id);
    }
    interface Protect{
        @GET(SEARCH)
        Single<JsonResponse<SearchResponse>> getSearchByUser(@Query("id")String user_id);
        @POST(SEARCH)
        Single<JsonResponse<MessageResponse>> saveSearch(@Body Search request);
        @DELETE(SEARCH)
        Single<JsonResponse<MessageResponse>> removeSearch(@Query("id")String search_id);
    }
}
