package com.odauday.ui.savedsearch;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public abstract class SavedSearchProvider {

    @ContributesAndroidInjector(modules = SavedSearchModule.class)
    @PerFragment
    abstract SavedSearchTabMainFragment provideSavedSearchTab();
}
