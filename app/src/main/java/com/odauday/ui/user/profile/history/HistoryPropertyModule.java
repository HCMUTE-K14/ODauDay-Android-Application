package com.odauday.ui.user.profile.history;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.HistoryRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 5/29/18.
 */

@Module
public class HistoryPropertyModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(
        HistoryPropertyViewModel historyPropertyViewModel) {
        return new ViewModelFactory<>(historyPropertyViewModel);
    }
    
    @Provides
    HistoryPropertyViewModel providePropertyDetailViewModel(HistoryRepository historyRepository) {
        return new HistoryPropertyViewModel(historyRepository);
    }
}
