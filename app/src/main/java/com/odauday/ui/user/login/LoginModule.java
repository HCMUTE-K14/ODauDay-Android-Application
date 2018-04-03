package com.odauday.ui.user.login;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.UserRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/24/18.
 */
@Module
public class LoginModule {

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(LoginViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }

    @Provides
    LoginViewModel provideLoginViewModel(UserRepository userRepository) {
        return new LoginViewModel(userRepository);

    }
}
