package com.odauday.data.local.favorite;

import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 5/5/18.
 */
public interface FavoriteService {
    
    Single<Long> create(FavoriteProperty favoriteProperty);
    
    Single<Long> save(List<FavoriteProperty> favoriteProperties);
    
    Single<Long> delete(FavoriteProperty favoriteProperty);
    
    Single<List<FavoriteProperty>> findFavoriteByUserId(String userId);
}
