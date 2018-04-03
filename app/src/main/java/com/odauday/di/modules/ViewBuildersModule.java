package com.odauday.di.modules;

import com.odauday.di.scopes.PerActivity;
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
    @PerActivity
    abstract FragmentDemo bindFragmentDemo();
}
