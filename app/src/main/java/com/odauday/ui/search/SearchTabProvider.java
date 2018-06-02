package com.odauday.ui.search;

import com.odauday.di.scopes.PerFragment;
import com.odauday.ui.search.listview.ListViewProvider;
import com.odauday.ui.search.mapview.MapViewFragmentProvider;
import com.odauday.ui.search.navigation.FilterNavigationProvider;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module(
    includes = {
        FilterNavigationProvider.class,
        MapViewFragmentProvider.class,
        ListViewProvider.class
    }
)
public abstract class SearchTabProvider {
    
    @ContributesAndroidInjector(modules = {SearchTabModule.class})
    @PerFragment
    abstract SearchTabMainFragment provideSearchTab();
}
