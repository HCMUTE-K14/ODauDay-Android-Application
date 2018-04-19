package com.odauday.di.modules;

import com.odauday.MainActivity;
import com.odauday.MainActivityModule;
import com.odauday.di.scopes.PerActivity;
import com.odauday.di.scopes.PerFragment;
import com.odauday.ui.alert.AlertTabProvider;
import com.odauday.ui.favorite.FavoriteTabProvider;
import com.odauday.ui.more.MoreTabProvider;
import com.odauday.ui.propertymanager.ActivityPropertyManager;
import com.odauday.ui.propertymanager.PropertyManagerModule;
import com.odauday.ui.savedsearch.SavedSearchProvider;
import com.odauday.ui.search.SearchTabProvider;
import com.odauday.ui.user.forgotpassword.ForgotPasswordActivity;
import com.odauday.ui.user.forgotpassword.ForgotPasswordModule;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.user.login.LoginModule;
import com.odauday.ui.user.register.RegisterActivity;
import com.odauday.ui.user.register.RegisterModule;
import com.odauday.ui.user.tag.ActivityDemo;
import com.odauday.ui.user.tag.FragmentDemo;
import com.odauday.ui.user.tag.TagModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 2/27/18.
 */

@Module
public abstract class ViewBuildersModule {
    
    @ContributesAndroidInjector(modules = LoginModule.class)
    @PerActivity
    abstract LoginActivity bindLoginActivity();
    
    
    @ContributesAndroidInjector(modules = RegisterModule.class)
    @PerActivity
    abstract RegisterActivity bindRegisterActivity();
    
    @ContributesAndroidInjector(modules = ForgotPasswordModule.class)
    @PerActivity
    abstract ForgotPasswordActivity bindForgotPasswordActivity();
    

    @ContributesAndroidInjector(modules = TagModule.class)
    @PerActivity
    abstract ActivityDemo bindActivityDemo();
    
    @ContributesAndroidInjector(modules = TagModule.class)
    @PerFragment
    abstract FragmentDemo bindFragmentDemo();

    @ContributesAndroidInjector(modules = {
              SearchTabProvider.class,
              AlertTabProvider.class,
              FavoriteTabProvider.class,
              SavedSearchProvider.class,
              MoreTabProvider.class,
              MainActivityModule.class
    })
    @PerActivity
    abstract MainActivity bindMainActivity();
    
    @ContributesAndroidInjector(modules = PropertyManagerModule.class)
    @PerActivity
    abstract ActivityPropertyManager provideActivityPropertyManager();
}
