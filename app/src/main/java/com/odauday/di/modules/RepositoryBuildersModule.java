package com.odauday.di.modules;

import com.odauday.SchedulersExecutor;
import com.odauday.data.RecentTagRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.tag.RecentTagService;
import com.odauday.data.remote.user.UserService;
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
    
    //--------------------------LOCAL---------------------------//
    
    @Provides
    @Singleton
    RecentTagRepository provideRecentTagRepository(
              RecentTagService recentTagService,
              PreferencesHelper preferencesHelper,
              SchedulersExecutor schedulersExecutor) {
        return new RecentTagRepository(
                  recentTagService,
                  preferencesHelper,
                  schedulersExecutor);
    }
}
