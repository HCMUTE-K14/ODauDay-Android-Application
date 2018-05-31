package com.odauday.ui.user.profile.detail;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 5/30/18.
 */
@Module
public abstract class ProfileDetailProvider {
    
    @ContributesAndroidInjector(modules = {ProfileDetailModule.class})
    @PerFragment
    abstract ProfileDetailFragment provideProfileDetailFragment();
}
