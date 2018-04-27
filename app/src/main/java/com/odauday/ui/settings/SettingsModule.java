package com.odauday.ui.settings;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.HistoryRepository;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 4/27/2018.
 */
@Module
public class SettingsModule {
    
    @Provides
    ChooseLanguageHelper provideChooseLanguageHelper(PreferencesHelper preferencesHelper) {
        return new ChooseLanguageHelper(preferencesHelper);
    }
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(SettingsViewModel settingsViewModel) {
        return new ViewModelFactory<>(settingsViewModel);
    }
    
    @Provides
    SettingsViewModel provideSettingsViewModel(HistoryRepository historyRepository) {
        return new SettingsViewModel(historyRepository);
    }
}
