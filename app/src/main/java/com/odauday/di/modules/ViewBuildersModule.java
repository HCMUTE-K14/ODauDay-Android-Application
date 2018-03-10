package com.odauday.di.modules;

import com.odauday.MainActivity;
import com.odauday.MainActivityModule;
import com.odauday.di.scopes.PerActivity;
import com.odauday.ui.test.GithubUserActivity;
import com.odauday.ui.test.GithubUserModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 2/27/18.
 */

@Module
public abstract class ViewBuildersModule {
    
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    abstract MainActivity bindMainActivity();
    
    @ContributesAndroidInjector(modules = GithubUserModule.class)
    @PerActivity
    abstract GithubUserActivity bindGitHubUserActivity();
    
}
