package com.odauday.ui.user.forgotpassword;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.UserRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 3/29/18.
 */
@Module
public class ForgotPasswordModule {

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(ForgotPasswordViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }

    @Provides
    ForgotPasswordViewModel provideForgotPasswordViewModel(UserRepository userRepository) {
        return new ForgotPasswordViewModel(userRepository);

    }
}
