package com.odauday.ui.propertymanager;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.PropertyRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 4/18/2018.
 */
@Module
public class PropertyManagerModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(PropertyManagerViewModel propertyManagerViewModel) {
        return new ViewModelFactory<>(propertyManagerViewModel);
    }
    @Provides
    PropertyManagerViewModel providePropertyManagerViewModel(PropertyRepository propertyRepository) {
        return new PropertyManagerViewModel(propertyRepository);
    }
}
