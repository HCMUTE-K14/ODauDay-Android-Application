package com.odauday.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.SavedSearchRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/30/18.
 */

@Module
public class SearchTabModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(SearchTabViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    SearchTabViewModel provideSearchTabViewModel(SavedSearchRepository savedSearchRepository, FavoriteRepository favoriteRepository) {
        return new SearchTabViewModel(savedSearchRepository, favoriteRepository);
    }
}
