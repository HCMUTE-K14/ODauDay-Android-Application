package com.odauday.ui.search.common.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/15/18.
 */
public class ReloadSearchEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 92;
    
    public ReloadSearchEvent() {
        super(REQUEST_CODE);
    }
}
