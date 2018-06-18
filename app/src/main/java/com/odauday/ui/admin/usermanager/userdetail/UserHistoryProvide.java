package com.odauday.ui.admin.usermanager.userdetail;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kunsubin on 6/11/2018.
 */
@Module
public abstract class UserHistoryProvide {
    @ContributesAndroidInjector(modules = UserHistoryModule.class)
    @PerFragment
    abstract FragmentUserHistory provideFragmentUserHistory();
}
