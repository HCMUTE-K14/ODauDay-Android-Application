package com.odauday.data.remote;

import static com.odauday.api.EndPoint.TAG;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.Tag;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 3/30/2018.
 */

public interface TagService {

    interface Public {

        @GET(TAG)
        Single<JsonResponse<List<Tag>>> getAllTag();
    }

    interface Protect {

        @POST(TAG)
        Single<JsonResponse<MessageResponse>> createTag(@Body Tag request);

        @PUT(TAG)
        Single<JsonResponse<MessageResponse>> updateTag(@Body Tag request);

        @DELETE(TAG)
        Single<JsonResponse<MessageResponse>> deleteTag(@Query("id") String id);
    }
}
