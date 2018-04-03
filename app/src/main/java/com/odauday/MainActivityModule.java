package com.odauday;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 2/28/18.
 */
@Module
public class MainActivityModule {

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainActivityViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }

    @Provides
    MainActivityViewModel provideMainActivityViewModel() {
        return new MainActivityViewModel();
    }


}
