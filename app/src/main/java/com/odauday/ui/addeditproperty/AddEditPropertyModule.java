package com.odauday.ui.addeditproperty;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/24/18.
 */

@Module
public class AddEditPropertyModule {
    
    @Provides
    ViewModelProvider.Factory mainViewProvider(AddEditPropertyViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    AddEditPropertyViewModel provideAddEditPropertyViewModel() {
        return new AddEditPropertyViewModel();
    }
}
