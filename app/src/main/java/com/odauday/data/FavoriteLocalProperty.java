package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.favorite.FavoriteProperty;
import com.odauday.data.local.favorite.FavoriteService;
import com.odauday.model.Favorite;
import io.reactivex.Single;

/**
 * Created by infamouSs on 5/5/18.
 */
public class FavoriteLocalProperty implements Repository {
    
    private final FavoriteService mFavoriteService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    public FavoriteLocalProperty(FavoriteService favoriteService,
        SchedulersExecutor schedulersExecutor) {
        this.mFavoriteService = favoriteService;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    
    public Single<Long> create(Favorite favorite) {
        return mFavoriteService.create(new FavoriteProperty(favorite))
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> create(FavoriteProperty favorite) {
        return mFavoriteService.create(favorite)
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> delete(FavoriteProperty favorite) {
        return mFavoriteService.delete(favorite)
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
}
