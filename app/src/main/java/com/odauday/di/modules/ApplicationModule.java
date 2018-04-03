package com.odauday.di.modules;

import android.content.Context;
import com.odauday.BuildConfig;
import com.odauday.RootApplication;
import com.odauday.SchedulersExecutor;
import com.odauday.api.EndPoint;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.di.scopes.PerApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 2/27/18.
 */
@PerApplication
@Module
public class ApplicationModule {

    @Provides
    Context provideContext(RootApplication application) {
        return application.getApplicationContext();

    }

    @Provides
    @Named("baseURL")
    String provideBaseURL() {
        return EndPoint.BASE_URL;
    }

    @Provides
    @Named("apiKey")
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }

    @Provides
    SchedulersExecutor provideSchedulersExecutor() {
        return new SchedulersExecutor();
    }
}
