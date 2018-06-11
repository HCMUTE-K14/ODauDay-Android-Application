package com.odauday.ui.admin.propertymanager;

import com.odauday.di.scopes.PerFragment;
import com.odauday.ui.favorite.FavoriteTabMainFragment;
import com.odauday.ui.favorite.FavoriteTabModule;
import com.odauday.ui.propertymanager.PropertyManagerModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kunsubin on 5/4/2018.
 */
@Module
public abstract class ConfirmPropertyProvider {
    
    @ContributesAndroidInjector(modules = ConfirmPropertyModule.class)
    @PerFragment
    abstract FragmentConfirmProperty provideFragmentConfirmProperty();
}
