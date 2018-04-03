package com.odauday.ui.alert;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public abstract class AlertTabProvider {

    @ContributesAndroidInjector(modules = AlertTabModule.class)
    @PerFragment
    abstract AlertTabMainFragment provideAlertTab();
}
