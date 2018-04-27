package com.odauday.ui.addeditproperty.step1;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/27/18.
 */
@Module
public abstract class Step1Provider {
    
    @ContributesAndroidInjector(modules = Step1Module.class)
    @PerFragment
    abstract Step1Fragment provideStep1Fragment();
}
