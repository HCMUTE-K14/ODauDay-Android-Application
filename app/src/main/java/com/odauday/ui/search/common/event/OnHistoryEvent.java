package com.odauday.ui.search.common.event;

import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 6/4/18.
 */
public class OnHistoryEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 124;
    
    private GeoLocation mGeoLocation;
    
    public OnHistoryEvent(GeoLocation geoLocation) {
        super(REQUEST_CODE);
        this.mGeoLocation = geoLocation;
    }
    
    public GeoLocation getGeoLocation() {
        return mGeoLocation;
    }
}
