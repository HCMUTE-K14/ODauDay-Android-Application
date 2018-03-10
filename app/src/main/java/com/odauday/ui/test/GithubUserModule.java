package com.odauday.ui.test;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.UserRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 2/28/18.
 */

@Module
public class GithubUserModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(GithubUserViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    GithubUserViewModel provideGitHubViewModel(UserRepository userRepository) {
        return new GithubUserViewModel(userRepository);
    }
}
