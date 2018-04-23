package com.odauday.ui.search.common.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/23/18.
 */
public class OnUpdateCriteriaEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 102;
    
    public OnUpdateCriteriaEvent() {
        super(REQUEST_CODE);
    }
}
