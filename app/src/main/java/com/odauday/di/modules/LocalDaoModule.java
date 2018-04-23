package com.odauday.di.modules;

import com.odauday.data.local.history.DaoSession;
import com.odauday.data.local.place.RecentSearchPlaceDao;
import com.odauday.data.local.tag.RecentTagDao;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 4/9/18.
 */
@Module
public class LocalDaoModule {
    
    @Provides
    @Singleton
    RecentTagDao provideRecentTagDao(DaoSession daoSession) {
        return daoSession.getRecentTagDao();
    }
    
    @Provides
    @Singleton
    RecentSearchPlaceDao provideRecentSearchPlaceDao(DaoSession daoSession) {
        return daoSession.getRecentSearchPlaceDao();
    }
}
