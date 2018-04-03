package com.odauday.di.modules;

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
    UserService.Public providePublicUserService(@Named("publicRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Public.class);
    }

    @Provides
    @Singleton
    UserService.Protect provideProtectUserService(@Named("protectRetrofit") Retrofit retrofit) {
        return retrofit.create(UserService.Protect.class);
    }
}
