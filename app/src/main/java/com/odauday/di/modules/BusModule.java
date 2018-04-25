package com.odauday.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/15/18.
 */
@Module
public class BusModule {
    
    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
        //                  .executorService(Executors.newFixedThreadPool(AppConfig.THREAD_POOL))
        //                  .eventInheritance(true)
        //                  .build();
    }
}
