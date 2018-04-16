package com.odauday.di.components;

import com.odauday.RootApplication;
import com.odauday.di.modules.ApplicationModule;
import com.odauday.di.modules.BusModule;
import com.odauday.di.modules.LocalDaoModule;
import com.odauday.di.modules.NetworkModule;
import com.odauday.di.modules.PreferenceModule;
import com.odauday.di.modules.RepositoryBuildersModule;
import com.odauday.di.modules.ServiceBuildersModule;
import com.odauday.di.modules.ViewBuildersModule;
import com.odauday.di.scopes.PerApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 2/27/18.
 */
@PerApplication
@Singleton
@Component(modules = {
          AndroidSupportInjectionModule.class,
          ApplicationModule.class,
          PreferenceModule.class,
          BusModule.class,
          NetworkModule.class,
          ServiceBuildersModule.class,
          LocalDaoModule.class,
          RepositoryBuildersModule.class,
          ViewBuildersModule.class})
public interface ApplicationComponent {
    
    void inject(RootApplication app);
    
    @Component.Builder
    interface Builder {
        
        @BindsInstance
        Builder application(RootApplication application);
        
        Builder preference(PreferenceModule preferenceModule);
        
        Builder bus(BusModule busModule);
        
        Builder network(NetworkModule networkModule);
        
        Builder localDAO(LocalDaoModule localDaoModule);
        
        Builder service(ServiceBuildersModule serviceBuildersModule);
        
        Builder repository(RepositoryBuildersModule repositoryBuildersModule);
        
        ApplicationComponent build();
    }
}