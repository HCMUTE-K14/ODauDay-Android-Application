package com.odauday;

import android.app.Activity;
import android.app.Application;
import com.odauday.config.AppConfig;
import com.odauday.data.local.tag.DaoSession;
import com.odauday.di.components.DaggerApplicationComponent;
import com.odauday.di.modules.LocalDaoModule;
import com.odauday.di.modules.NetworkModule;
import com.odauday.di.modules.RepositoryBuildersModule;
import com.odauday.di.modules.ServiceBuildersModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 2/27/18.
 */

public class RootApplication extends Application implements HasActivityInjector {
    
    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;
    
    @Inject
    DaoSession mDaoSession;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        if (AppConfig.isDebug()) {
            Timber.plant(new Timber.DebugTree());
        }
        
        DaggerApplicationComponent
                  .builder()
                  .application(this)
                  .network(new NetworkModule(this))
                  .localDAO(new LocalDaoModule())
                  .service(new ServiceBuildersModule())
                  .repository(new RepositoryBuildersModule())
                  .build()
                  .inject(this);
    }
    
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
