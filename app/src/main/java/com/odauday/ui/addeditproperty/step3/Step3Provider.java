package com.odauday.ui.addeditproperty.step3;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/27/18.
 */

@Module
public abstract class Step3Provider {
    
    @ContributesAndroidInjector(modules = Step3Module.class)
    @PerFragment
    abstract Step3Fragment provideStep3Fragment();
}
