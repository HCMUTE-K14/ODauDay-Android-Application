package com.odauday.ui.admin.propertymanager;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.AdminRepository;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.ui.favorite.FavoriteViewModel;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 5/4/2018.
 */
@Module
public class ConfirmPropertyModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(ConfirmPropertyModel confirmPropertyModel) {
        return new ViewModelFactory<>(confirmPropertyModel);
    }
    
    @Provides
    ConfirmPropertyModel provideConfirmProperty(AdminRepository adminRepository,PropertyRepository propertyRepository) {
        return new ConfirmPropertyModel(adminRepository,propertyRepository);
    }
}
