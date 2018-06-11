package com.odauday.ui.search.navigation;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.RecentTagRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/1/18.
 */
@Module
public class FilterNavigationModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(FilterNavigationViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    FilterNavigationViewModel provideFilterNavigationViewModel(
        RecentTagRepository recentTagRepository) {
        return new FilterNavigationViewModel(recentTagRepository);
    }
}
