package com.odauday.ui.user.profile.history;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 5/29/18.
 */
@Module
public abstract class HistoryPropertyProvider {
    
    @ContributesAndroidInjector(modules = {HistoryPropertyModule.class})
    @PerFragment
    abstract HistoryPropertyFragment provideHistoryPropertyFragment();
}
