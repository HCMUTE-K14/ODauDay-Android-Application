package com.odauday.ui.addeditproperty.step2;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/27/18.
 */
@Module
public abstract class Step2Provider {
    
    @ContributesAndroidInjector(modules = Step2Module.class)
    @PerFragment
    abstract Step2Fragment provideStep2Fragment();
}
