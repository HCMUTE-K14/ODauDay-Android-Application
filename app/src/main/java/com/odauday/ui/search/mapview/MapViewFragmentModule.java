package com.odauday.ui.search.mapview;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 4/11/18.
 */
@Module
public class MapViewFragmentModule {
    
    @Provides
    MapViewAdapter provideMapViewAdapter(Context context) {
        return new MapViewAdapter(context);
    }
    
}
