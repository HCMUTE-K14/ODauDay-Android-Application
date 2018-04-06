package com.odauday.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.UserRepository;
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
    SearchTabViewModel provideSearchTabViewModel(UserRepository userRepository) {
        return new SearchTabViewModel(userRepository);
    }
}
