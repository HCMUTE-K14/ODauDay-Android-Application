package com.odauday.ui.user.buypoint;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.PremiumRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 5/31/18.
 */
@Module
public class BuyPointModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(
        BuyPointViewModel profileUserViewModel) {
        return new ViewModelFactory<>(profileUserViewModel);
    }
    
    @Provides
    BuyPointViewModel provideBuyPointViewModel(PremiumRepository premiumRepository) {
        return new BuyPointViewModel(premiumRepository);
    }
}
