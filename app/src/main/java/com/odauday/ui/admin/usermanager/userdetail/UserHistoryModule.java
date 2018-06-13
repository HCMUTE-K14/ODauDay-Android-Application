package com.odauday.ui.admin.usermanager.userdetail;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.AdminRepository;
import com.odauday.ui.admin.propertymanager.ConfirmPropertyModel;
import com.odauday.ui.admin.usermanager.UserManagerModel;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 6/11/2018.
 */
@Module
public class UserHistoryModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(UserHistoryModel userHistoryModel) {
        return new ViewModelFactory<>(userHistoryModel);
    }
    @Provides
    UserHistoryModel provideUserHistory(AdminRepository adminRepository) {
        return new UserHistoryModel(adminRepository);
    }
}
