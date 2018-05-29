package com.odauday.ui.user.profile;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.HistoryRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 5/28/18.
 */
@Module
public class ProfileUserModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(
        ProfileUserViewModel profileUserViewModel) {
        return new ViewModelFactory<>(profileUserViewModel);
    }
    
    @Provides
    ProfileUserViewModel provideProfileUserViewModel(HistoryRepository historyRepository) {
        return new ProfileUserViewModel(historyRepository);
    }
}
