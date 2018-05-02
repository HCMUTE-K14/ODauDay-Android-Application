package com.odauday.ui.addeditproperty;

import android.arch.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import com.odauday.data.PropertyRepository;
import com.odauday.data.local.cache.PreferencesHelper;
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
    AddEditPropertyViewModel provideAddEditPropertyViewModel(
        PropertyRepository propertyRepository, PreferencesHelper preferencesHelper, Gson gson) {
        return new AddEditPropertyViewModel(propertyRepository, preferencesHelper, gson);
    }
}
