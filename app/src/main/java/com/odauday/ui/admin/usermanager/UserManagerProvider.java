package com.odauday.ui.admin.usermanager;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kunsubin on 5/10/2018.
 */
@Module
public abstract class UserManagerProvider {
    
    @ContributesAndroidInjector(modules = UserManagerModule.class)
    @PerFragment
    abstract FragmentUserManager provideFragmentUserManager();
}
