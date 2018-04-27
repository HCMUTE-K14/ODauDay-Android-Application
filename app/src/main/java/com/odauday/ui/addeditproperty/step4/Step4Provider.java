package com.odauday.ui.addeditproperty.step4;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/27/18.
 */
@Module
public abstract class Step4Provider {
    
    @ContributesAndroidInjector(modules = Step4Module.class)
    @PerFragment
    abstract Step4Fragment provideStep4Fragment();
}
