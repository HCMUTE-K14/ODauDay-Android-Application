package com.odauday.data.remote.property;

import static com.odauday.api.EndPoint.SEARCH_PROPERTY;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by infamouSs on 4/14/18.
 */
public interface SearchService {
    
    @POST(SEARCH_PROPERTY)
    Single<JsonResponse<SearchResult>> search(@Body SearchRequest searchRequest);
}
