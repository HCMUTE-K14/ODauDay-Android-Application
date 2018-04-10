package com.odauday.data.remote;

import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.Favorite;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import static com.odauday.api.EndPoint.FAVORITE;
/**
 * Created by kunsubin on 4/4/2018.
 */

public interface FavoriteService {
    interface Public{
        @GET(FAVORITE)
        Single<JsonResponse<FavoriteResponse>> getFavoritePropertyByUser(@Query("id")String user_id);
    }
    interface Protect{
        @GET(FAVORITE)
        Single<JsonResponse<FavoriteResponse>> getFavoritePropertyByUser(@Query("id")String user_id);
        @POST(FAVORITE)
        Single<JsonResponse<MessageResponse>> checkFavorite(@Body Favorite request);
        @DELETE(FAVORITE)
        Single<JsonResponse<MessageResponse>> unCheckFavorite(@Query("property_id")String propertyId);
    }
}
