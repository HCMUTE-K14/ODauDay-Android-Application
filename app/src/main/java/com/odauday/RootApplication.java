package com.odauday;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.odauday.config.AppConfig;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.favorite.DaoSession;
import com.odauday.di.components.DaggerApplicationComponent;
import com.odauday.di.modules.BusModule;
import com.odauday.di.modules.LocalDaoModule;
import com.odauday.di.modules.NetworkModule;
import com.odauday.di.modules.PreferenceModule;
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
    
    private static Context mContext;
    
    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;
    
    @Inject
    DaoSession mDaoSession;
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        RootApplication.mContext = getApplicationContext();

        if (AppConfig.isDebug()) {
            Timber.plant(new Timber.DebugTree());
        }

        DaggerApplicationComponent
                  .builder()
                  .application(this)
                  .preference(new PreferenceModule())
                  .bus(new BusModule())
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
