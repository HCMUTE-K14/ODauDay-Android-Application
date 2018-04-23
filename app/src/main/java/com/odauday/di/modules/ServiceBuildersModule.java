package com.odauday.di.modules;

import com.odauday.data.local.place.RecentSearchPlaceDao;
import com.odauday.data.local.place.RecentSearchPlaceService;
import com.odauday.data.local.place.RecentSearchPlaceServiceImpl;
import com.odauday.data.local.tag.RecentTagDao;
import com.odauday.data.local.tag.RecentTagService;
import com.odauday.data.local.tag.RecentTagServiceImpl;
import com.odauday.data.remote.autocompleteplace.AutoCompletePlaceService;
import com.odauday.data.remote.property.SearchService;
import com.odauday.data.remote.user.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by infamouSs on 2/28/18.
 */

@Module
public class ServiceBuildersModule {
    
    @Provides
    @Singleton
    UserService.Public providePublicUserService(@Named("publicRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Public.class);
    }
    
    @Provides
    @Singleton
    UserService.Protect provideProtectUserService(@Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Protect.class);
    }
    
    @Provides
    @Singleton
    SearchService provideSearchService(@Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(SearchService.class);
    }
    
    @Provides
    @Singleton
    AutoCompletePlaceService provideAutoCompletePlaceService(
              @Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(AutoCompletePlaceService.class);
    }
    
    //--------------------------LOCAL---------------------------//
    
    @Provides
    @Singleton
    RecentTagService provideRecentTagService(RecentTagDao recentTagDao) {
        return new RecentTagServiceImpl(recentTagDao);
    }
    
    
    @Provides
    @Singleton
    RecentSearchPlaceService provideRecentSearchPlaceService(
              RecentSearchPlaceDao recentSearchPlaceDao) {
        return new RecentSearchPlaceServiceImpl(recentSearchPlaceDao);
    }
    
}
