package com.odauday.ui.more;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public abstract class MoreTabProvider {
    
    @ContributesAndroidInjector(modules = MoreTabModule.class)
    @PerFragment
    abstract MoreTabMainFragment provideMoreTab();
}
