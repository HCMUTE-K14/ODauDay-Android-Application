package com.odauday.ui.search.common.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/18/18.
 */
public class NeedCloseVitalPropertyEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 92;
    
    public NeedCloseVitalPropertyEvent() {
        super(REQUEST_CODE);
    }
}
