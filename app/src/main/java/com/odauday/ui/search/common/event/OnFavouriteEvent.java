package com.odauday.ui.search.common.event;

import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 5/3/18.
 */
public class OnFavouriteEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 112;
    
    
    private PropertyResultEntry result;
    
    public OnFavouriteEvent(PropertyResultEntry resultEntry) {
        super(REQUEST_CODE);
        this.result = resultEntry;
    }
    
    public PropertyResultEntry getResult() {
        return result;
    }
}
