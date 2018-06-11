package com.odauday.ui.search.listview;

import com.odauday.di.scopes.PerChildFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 6/1/18.
 */
@Module
public abstract class ListViewProvider {
    
    @ContributesAndroidInjector(modules = ListViewModule.class)
    @PerChildFragment
    abstract ListViewFragment provideListViewFragment();
}
