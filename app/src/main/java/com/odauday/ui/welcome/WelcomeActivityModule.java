package com.odauday.ui.welcome;

import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.ui.settings.ChooseLanguageHelper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 5/2/18.
 */
@Module
public class WelcomeActivityModule {
    
    @Provides
    ChooseLanguageHelper provideChooseLanguageHelper(PreferencesHelper preferencesHelper) {
        return new ChooseLanguageHelper(preferencesHelper);
    }
}
