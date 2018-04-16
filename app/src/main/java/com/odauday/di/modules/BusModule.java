package com.odauday.di.modules;

import com.odauday.config.AppConfig;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.Executors;
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
        return EventBus.builder()
                  .executorService(Executors.newFixedThreadPool(AppConfig.THREAD_POOL))
                  .eventInheritance(true)
                  .build();
    }
}
