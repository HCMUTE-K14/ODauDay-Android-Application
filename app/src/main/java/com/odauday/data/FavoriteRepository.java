package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.FavoriteService;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.FavoriteException;
import com.odauday.model.Favorite;
import com.odauday.model.ShareFavorite;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;


/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteRepository implements Repository {
    
    private final FavoriteService mFavoriteService;
    private final SchedulersExecutor mSchedulersExecutor;
    private final PreferencesHelper mPreferencesHelper;
    
    @Inject
    public FavoriteRepository(FavoriteService favoriteService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        mFavoriteService = favoriteService;
        mSchedulersExecutor = schedulersExecutor;
        mPreferencesHelper = preferencesHelper;
    }
    
    public Single<FavoriteResponse> getFavoritePropertyByUser(String user_id) {
        Single<JsonResponse<FavoriteResponse>> result = mFavoriteService
            .getFavoritePropertyByUser(user_id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof FavoriteException) {
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> checkFavorite(Favorite favorite) {
        Single<JsonResponse<MessageResponse>> result = mFavoriteService.checkFavorite(favorite);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof FavoriteException) {
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> checkFavorite(String propertyId) {
        Favorite favorite = new Favorite();
        favorite.setPropertyId(propertyId);
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        favorite.setUserId(userId);
        
        return checkFavorite(favorite);
        
    }
    
    public Single<MessageResponse> unCheckFavorite(String propertyId) {
        Single<JsonResponse<MessageResponse>> result = mFavoriteService.unCheckFavorite(propertyId);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof FavoriteException) {
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> unCheckFavorites(List<Favorite> list) {
        Single<JsonResponse<MessageResponse>> result = mFavoriteService.unCheckFavorites(list);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof FavoriteException) {
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> shareFavorite(ShareFavorite shareFavorite) {
        Single<JsonResponse<MessageResponse>> result = mFavoriteService
            .shareFavorite(shareFavorite);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new FavoriteException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof FavoriteException) {
                        throw ex;
                    }
                    throw new FavoriteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
