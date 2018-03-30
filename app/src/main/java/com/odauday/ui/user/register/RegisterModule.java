package com.odauday.ui.user.register;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.UserRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/27/18.
 */
@Module
public class RegisterModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(RegisterViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    RegisterViewModel provideRegisterViewModel(UserRepository userRepository) {
        return new RegisterViewModel(userRepository);
    }
}
