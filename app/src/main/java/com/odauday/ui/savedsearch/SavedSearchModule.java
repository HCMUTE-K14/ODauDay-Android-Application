package com.odauday.ui.savedsearch;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.SavedSearchRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public class SavedSearchModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(SavedSearchViewModel savedSearchViewModel) {
        return new ViewModelFactory<>(savedSearchViewModel);
    }
    
    @Provides
    SavedSearchViewModel provideSearchViewModel(SavedSearchRepository savedSearchRepository) {
        return new SavedSearchViewModel(savedSearchRepository);
    }
}
