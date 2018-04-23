package com.odauday.ui.search.common.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/19/18.
 */
public class ReInitMapFragmentEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 94;
    
    public ReInitMapFragmentEvent() {
        super(REQUEST_CODE);
    }
}
