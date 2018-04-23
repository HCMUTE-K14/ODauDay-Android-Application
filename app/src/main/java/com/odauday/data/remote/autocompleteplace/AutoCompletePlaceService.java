package com.odauday.data.remote.autocompleteplace;

import static com.odauday.api.EndPoint.AUTOCOMPLETE_PLACE;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.data.remote.model.JsonResponse;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 4/22/18.
 */
public interface AutoCompletePlaceService {
    
    @GET(AUTOCOMPLETE_PLACE)
    Single<JsonResponse<List<AutoCompletePlace>>> search(@Query("key") String keyword);
}
