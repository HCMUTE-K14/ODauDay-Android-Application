package com.odauday.ui.favorite;

import com.odauday.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public abstract class FavoriteTabProvider {
    
    @ContributesAndroidInjector(modules = FavoriteTabModule.class)
    @PerFragment
    abstract FavoriteTabMainFragment provideFavoriteTab();
}
