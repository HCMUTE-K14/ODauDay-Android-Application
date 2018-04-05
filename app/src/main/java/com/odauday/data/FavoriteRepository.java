package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.FavoriteService;
import com.odauday.data.remote.FavoriteService.Protect;
import com.odauday.data.remote.FavoriteService.Public;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.FavoriteException;
import com.odauday.exception.TagException;
import com.odauday.model.Favorite;
import io.reactivex.Single;
import javax.inject.Inject;


/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteRepository implements Repository {
    
    private final FavoriteService.Public mPublicFavoriteService;
    private final FavoriteService.Protect mProtectFavoriteService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public FavoriteRepository(Public publicFavoriteService,
        Protect protectFavoriteService,
        SchedulersExecutor schedulersExecutor) {
        mPublicFavoriteService = publicFavoriteService;
        mProtectFavoriteService = protectFavoriteService;
        mSchedulersExecutor = schedulersExecutor;
    }
    public Single<FavoriteResponse> getFavoritePropertyByUser(String user_id) {
        Single<JsonResponse<FavoriteResponse>> result = mPublicFavoriteService.getFavoritePropertyByUser(user_id);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof FavoriteException){
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> checkFavorite(Favorite favorite) {
        Single<JsonResponse<MessageResponse>> result = mProtectFavoriteService.checkFavorite(favorite);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof FavoriteException){
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> unCheckFavorite(String propertyId) {
        Single<JsonResponse<MessageResponse>> result = mProtectFavoriteService.unCheckFavorite(propertyId);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof FavoriteException){
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
