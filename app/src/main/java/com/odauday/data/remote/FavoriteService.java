package com.odauday.data.remote;

import static com.odauday.api.EndPoint.FAVORITE;

import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.Favorite;
import com.odauday.model.PropertyID;
import com.odauday.model.ShareFavorite;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kunsubin on 4/4/2018.
 */

public interface FavoriteService {
    
    @GET(FAVORITE)
    Single<JsonResponse<FavoriteResponse>> getFavoritePropertyByUser(@Query("id") String user_id);
    
    @POST(FAVORITE)
    Single<JsonResponse<MessageResponse>> checkFavorite(@Body Favorite request);
    
    @DELETE(FAVORITE)
    Single<JsonResponse<MessageResponse>> unCheckFavorite(@Query("property_id") String propertyId);
    
    @HTTP(method = "DELETE", path = FAVORITE + "/all", hasBody = true)
    Single<JsonResponse<MessageResponse>> unCheckFavorites(@Body List<PropertyID> list);
    
    @POST(FAVORITE + "/share")
    Single<JsonResponse<MessageResponse>> shareFavorite(@Body ShareFavorite shareFavorite);
    
    
}
