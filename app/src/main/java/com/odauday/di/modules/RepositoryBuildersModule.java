package com.odauday.di.modules;

import com.odauday.SchedulersExecutor;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.TagRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.FavoriteService;
import com.odauday.data.remote.TagService;
import com.odauday.data.remote.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 2/28/18.
 */
@Module
public class RepositoryBuildersModule {
    
    @Provides
    @Singleton
    UserRepository provideUserRepository(
        UserService.Public publicUserService,
        UserService.Protect protectUserService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        return new UserRepository(
            publicUserService,
            protectUserService,
            preferencesHelper,
            schedulersExecutor);
    }
    
    @Provides
    @Singleton
    TagRepository provideTagRepository(TagService.Public publicTagService,
        TagService.Protect protectTagService, SchedulersExecutor schedulersExecutor) {
        return new TagRepository(publicTagService, protectTagService, schedulersExecutor);
    }
    
    @Provides
    @Singleton
    FavoriteRepository provideFavoriteRespository(FavoriteService.Public publicFavoriteService,
        FavoriteService.Protect protectFavoriteService, SchedulersExecutor schedulersExecutor){
        return new FavoriteRepository(publicFavoriteService,protectFavoriteService,schedulersExecutor);
    }
}
