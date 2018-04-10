package com.odauday.di.modules;

import com.odauday.data.remote.FavoriteService;
import com.odauday.data.remote.SearchService;
import com.odauday.data.remote.TagService;
import com.odauday.data.remote.UserService;
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
    UserService.Public PublicUserService(@Named("publicRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Public.class);
    }
    
    @Provides
    @Singleton
    UserService.Protect provideProtectUserService(@Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Protect.class);
    }
    
    @Provides
    @Singleton
    TagService.Public providePublicTagService(@Named("publicRetrofit") Retrofit retrofit) {
        return retrofit.create(TagService.Public.class);
    }
    
    @Provides
    @Singleton
    TagService.Protect provideProtectTagService(@Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(TagService.Protect.class);
    }
    
    @Provides
    @Singleton
    FavoriteService.Public providePublicFavoriteService(
        @Named("publicRetrofit") Retrofit retrofit) {
        return retrofit.create(FavoriteService.Public.class);
    }
    
    @Provides
    @Singleton
    FavoriteService.Protect provideProtectFavoriteService(
        @Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(FavoriteService.Protect.class);
    }
    
    @Provides
    @Singleton
    SearchService.Public providePublicSearchSevice(@Named("publicRetrofit") Retrofit retrofit){
        return retrofit.create(SearchService.Public.class);
    }
    
    @Provides
    @Singleton
    SearchService.Protect provideProtectSearchSevice(@Named("protectRetrofit") Retrofit retrofit){
        return retrofit.create(SearchService.Protect.class);
    }
    
}
