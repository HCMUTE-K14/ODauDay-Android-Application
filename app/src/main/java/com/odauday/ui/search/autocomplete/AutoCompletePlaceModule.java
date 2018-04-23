package com.odauday.ui.search.autocomplete;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.AutoCompletePlaceRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/22/18.
 */

@Module
public class AutoCompletePlaceModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(AutoCompletePlaceViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    AutoCompletePlaceViewModel provideSearchTabViewModel(
              AutoCompletePlaceRepository autoCompletePlaceRepository) {
        return new AutoCompletePlaceViewModel(autoCompletePlaceRepository);
    }
}
