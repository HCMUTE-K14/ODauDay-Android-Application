package com.odauday.data.local.favorite;

import com.odauday.data.local.history.HistoryPropertyDao.Properties;
import io.reactivex.Single;
import java.util.List;
import org.greenrobot.greendao.query.DeleteQuery;

/**
 * Created by infamouSs on 5/5/18.
 */
public class FavoriteServiceImpl implements FavoriteService {
    
    private final FavoritePropertyDao mFavoritePropertyDao;
    
    public FavoriteServiceImpl(FavoritePropertyDao favoritePropertyDao) {
        this.mFavoritePropertyDao = favoritePropertyDao;
    }
    
    @Override
    public Single<Long> create(FavoriteProperty favoriteProperty) {
        return Single.fromCallable(() -> mFavoritePropertyDao.insert(favoriteProperty));
    }
    
    @Override
    public Single<Long> save(List<FavoriteProperty> favoriteProperties) {
        return Single.fromCallable(() -> {
            for (FavoriteProperty favoriteProperty : favoriteProperties) {
                boolean isExists = mFavoritePropertyDao
                                       .queryBuilder()
                                       .where(Properties.PropertyId
                                               .eq(favoriteProperty.getId()),
                                           Properties.UserId
                                               .eq(favoriteProperty.getUserId()))
                                       .count() > 0;
                
                if (!isExists) {
                    mFavoritePropertyDao.insert(favoriteProperty);
                }
            }
            return 1L;
        });
    }
    
    @Override
    public Single<Long> delete(FavoriteProperty favoriteProperty) {
        return Single.fromCallable(() -> {
            DeleteQuery deleteQuery = mFavoritePropertyDao
                .queryBuilder()
                .where(Properties.PropertyId
                        .eq(favoriteProperty.getPropertyId()),
                    Properties.UserId
                        .eq(favoriteProperty.getUserId()))
                .buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
            return 1L;
        });
    }
    
    @Override
    public Single<List<FavoriteProperty>> findFavoriteByUserId(String userId) {
        return Single.fromCallable(() -> mFavoritePropertyDao
            .queryBuilder()
            .where(Properties.UserId.eq(userId))
            .list());
    }
}
