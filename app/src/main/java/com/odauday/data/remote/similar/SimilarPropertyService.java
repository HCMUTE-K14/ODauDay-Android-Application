package com.odauday.data.remote.similar;

import static com.odauday.api.EndPoint.SIMILAR_PROPERTY;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.ui.propertydetail.common.SimilarProperty;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by infamouSs on 6/1/18.
 */
public interface SimilarPropertyService {
    
    @GET(SIMILAR_PROPERTY + "/{id}")
    Single<JsonResponse<List<SimilarProperty>>> get(@Path("id") String id);
}
