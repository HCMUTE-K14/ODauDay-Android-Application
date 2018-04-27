package com.odauday.ui.addeditproperty.step3;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.RecentTagRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/27/18.
 */
@Module
public class Step3Module {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(Step3ViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    Step3ViewModel provideRegisterViewModel(RecentTagRepository recentTagRepository) {
        return new Step3ViewModel(recentTagRepository);
    }
}
