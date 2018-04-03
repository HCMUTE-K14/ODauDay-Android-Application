package com.odauday.ui.search.navigation;

import com.odauday.di.scopes.PerChildFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/1/18.
 */
@Module
public abstract class FilterNavigationProvider {

    @ContributesAndroidInjector(modules = FilterNavigationModule.class)
    @PerChildFragment
    abstract FilterNavigationFragment provideFilterNavigation();
}
