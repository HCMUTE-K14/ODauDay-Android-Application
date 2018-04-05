package com.odauday.ui.alert;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/31/18.
 */

@Module
public class AlertTabModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(AlertTabViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    AlertTabViewModel provideAlertTabViewModel() {
        return new AlertTabViewModel();
    }
}
