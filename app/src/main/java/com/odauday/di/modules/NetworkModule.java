package com.odauday.di.modules;

import android.content.Context;
import com.google.gson.Gson;
import com.odauday.api.APIHeader.ProtectApiHeader;
import com.odauday.api.APIHeader.PublicApiHeader;
import com.odauday.api.APIHelper;
import com.odauday.api.ApiLanguageSupport;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by infamouSs on 2/27/18.
 */

@Module(includes = ApiHeaderModule.class)
public class NetworkModule {
    
    private final APIHelper mApiHelper;
    
    public NetworkModule(Context context) {
        this.mApiHelper = new APIHelper(context);
    }
    
    @Provides
    @Named("lang")
    String provideLanguageAPI(PreferencesHelper preferencesHelper) {
        return preferencesHelper.get(PrefKey.LANGUAGE_API, ApiLanguageSupport.EN.getValue());
    }
    
    @Provides
    @Singleton
    APIHelper provideAPIUtils(Context context) {
        return mApiHelper;
    }
    
    @Provides
    @Singleton
    Gson provideGson() {
        return mApiHelper.createDefaultGson();
    }
    
    @Provides
    @Singleton
    Cache provideCache(Context context) {
        return mApiHelper.createCache(context);
    }
    
    @Provides
    @Singleton
    @Named("protectInterceptor")
    Interceptor provideProtectInterceptor(
              @Named("protectApiHeader") ProtectApiHeader protectApiHeader) {
        return mApiHelper.createInterceptor(protectApiHeader);
    }
    
    @Provides
    @Singleton
    @Named("publicInterceptor")
    Interceptor providePublicInterceptor(
              @Named("publicApiHeader") PublicApiHeader publicApiHeader) {
        return mApiHelper.createInterceptor(publicApiHeader);
    }
    
    @Provides
    @Singleton
    @Named("languageInterceptor")
    Interceptor provideLanguageInterceptor(@Named("lang") String lang) {
        return mApiHelper.createLanguageSupportInterceptor(lang);
    }
    
    @Provides
    @Singleton
    @Named("protectHttpClient")
    OkHttpClient provideProtectHttpClient(
              Cache cache,
              @Named("protectInterceptor") Interceptor protectInterceptor,
              @Named("languageInterceptor") Interceptor languageInterceptor) {
        return mApiHelper.createClient(cache, protectInterceptor, languageInterceptor);
    }
    
    @Provides
    @Singleton
    @Named("publicHttpClient")
    OkHttpClient providePublicHttpClient(
              Cache cache,
              @Named("publicInterceptor") Interceptor publicInterceptor,
              @Named("languageInterceptor") Interceptor languageInterceptor) {
        return mApiHelper.createClient(cache, publicInterceptor, languageInterceptor);
    }
    
    @Provides
    @Singleton
    @Named("protectRetrofit")
    Retrofit provideProtectRetrofit(
              @Named("baseURL") String baseURL,
              @Named("protectHttpClient") OkHttpClient client,
              Gson gson) {
        return mApiHelper.createRetrofit(baseURL, client, gson);
    }
    
    @Provides
    @Singleton
    @Named("defaultRetrofit")
    Retrofit provideDefaultRetrofit(
              @Named("baseURL") String baseURL,
              Cache cache,
              Gson gson) {
        OkHttpClient client = mApiHelper
                  .createClient(cache, mApiHelper.createDefaultInterceptor(), null);
        return mApiHelper.createRetrofit(baseURL,
                  client,
                  gson);
        
    }
    
    @Provides
    @Singleton
    @Named("publicRetrofit")
    Retrofit providePublicRetrofit(
              @Named("baseURL") String baseURL,
              @Named("publicHttpClient") OkHttpClient client,
              Gson gson) {
        return mApiHelper.createRetrofit(baseURL, client, gson);
    }
}