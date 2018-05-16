package com.odauday.ui.admin.usermanager;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.AdminRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.data.UserRepository;
import com.odauday.ui.admin.propertymanager.ConfirmPropertyModel;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 5/10/2018.
 */
@Module
public class UserManagerModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(ConfirmPropertyModel confirmPropertyModel) {
        return new ViewModelFactory<>(confirmPropertyModel);
    }
    @Provides
    UserManagerModel provideUserManager(AdminRepository adminRepository) {
        return new UserManagerModel(adminRepository);
    }
}
