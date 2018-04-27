package com.odauday.di.modules;

import com.odauday.api.APIHeader;
import com.odauday.api.APIHeader.ProtectApiHeader;
import com.odauday.api.APIHeader.PublicApiHeader;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 2/28/18.
 */
@Module
public class ApiHeaderModule {
    
    @Provides
    @Singleton
    @Named("protectApiHeader")
    APIHeader.ProtectApiHeader provideProtectApiHeader(
        @Named("apiKey") String apiKey,
        PreferencesHelper preferencesHelper) {
        
        return new APIHeader.ProtectApiHeader(
                  preferencesHelper.get(PrefKey.ACCESS_TOKEN, ""),
                  apiKey,
                  preferencesHelper.get(PrefKey.USER_ID, ""));
    }
    
    @Provides
    @Singleton
    @Named("publicApiHeader")
    APIHeader.PublicApiHeader providePublicApiHeader(
        @Named("apiKey") String apiKey) {
        return new PublicApiHeader(apiKey);
    }
    
    
    @Provides
    @Singleton
    APIHeader provideApiHeader(ProtectApiHeader protectApiHeader, PublicApiHeader publicApiHeader) {
        return new APIHeader(protectApiHeader, publicApiHeader);
    }
}
