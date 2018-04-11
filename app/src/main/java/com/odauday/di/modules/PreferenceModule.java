package com.odauday.di.modules;

import android.content.Context;
import com.google.gson.Gson;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.local.cache.PreferencesHelper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 4/11/18.
 */

@Module
public class PreferenceModule {
    
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }
    
    @Provides
    @Singleton
    MapPreferenceHelper provideMapPreferencesHelper(PreferencesHelper preferencesHelper,
              Gson gson) {
        return new MapPreferenceHelper(preferencesHelper, gson);
    }
}
