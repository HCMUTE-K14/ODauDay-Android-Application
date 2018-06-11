package com.odauday.ui.propertydetail;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 5/12/18.
 */

@Module
public class PropertyDetailModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(
        PropertyDetailViewModel propertyDetailViewModel) {
        return new ViewModelFactory<>(propertyDetailViewModel);
    }
    
    @Provides
    PropertyDetailViewModel providePropertyDetailViewModel(
        PropertyRepository propertyRepository,
        PreferencesHelper preferencesHelper,
        FavoriteRepository favoriteRepository) {
        return new PropertyDetailViewModel(propertyRepository,
            favoriteRepository, preferencesHelper);
    }
}
