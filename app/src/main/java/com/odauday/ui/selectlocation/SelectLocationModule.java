package com.odauday.ui.selectlocation;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.GeoInfoRepository;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/25/18.
 */
@Module
public class SelectLocationModule {
    
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(SelectLocationViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    
    @Provides
    SelectLocationViewModel provideSelectLocationViewModel(GeoInfoRepository geoInfoRepository) {
        return new SelectLocationViewModel(geoInfoRepository);
    }
}
