package com.odauday.di.modules;

import android.content.Context;
import com.odauday.BuildConfig;
import com.odauday.RootApplication;
import com.odauday.SchedulersExecutor;
import com.odauday.api.EndPoint;
import com.odauday.config.AppConfig;
import com.odauday.data.local.DatabaseHelper;
import com.odauday.data.local.favorite.DaoMaster;
import com.odauday.data.local.favorite.DaoMaster.DevOpenHelper;
import com.odauday.data.local.favorite.DaoMaster.OpenHelper;
import com.odauday.data.local.favorite.DaoSession;
import com.odauday.di.scopes.PerApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by infamouSs on 2/27/18.
 */
@PerApplication
@Module
public class ApplicationModule {
    
    @Provides
    Context provideContext(RootApplication application) {
        return application.getApplicationContext();
    }
    
    @Provides
    @Named("baseURL")
    String provideBaseURL() {
        return EndPoint.BASE_URL;
    }
    
    @Provides
    @Named("apiKey")
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }
    
    @Provides
    @Named("databaseName")
    String provideDatabaseName() {
        return AppConfig.DATABASE_NAME;
    }
    
    @Provides
    @Singleton
    DaoSession provideDaoSession(RootApplication application,
              @Named("databaseName") String databaseName) {
        OpenHelper helper;
        if (AppConfig.isDebug()) {
            helper = new DevOpenHelper(application, databaseName);
            QueryBuilder.LOG_SQL = true;
        } else {
            helper = new DatabaseHelper(application, databaseName);
        }
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
    
    
    @Provides
    SchedulersExecutor provideSchedulersExecutor() {
        return new SchedulersExecutor();
    }
}
