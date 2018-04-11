package com.odauday.ui.search.mapview;

import com.odauday.di.scopes.PerChildFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 4/11/18.
 */
@Module
public abstract class MapViewFragmentProvider {
    
    @ContributesAndroidInjector(modules = MapViewFragmentModule.class)
    @PerChildFragment
    abstract MapViewFragment provideMapViewFragment();
}
