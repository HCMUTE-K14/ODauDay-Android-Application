package com.odauday.ui.user.profile.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 5/29/18.
 */
public class ClearHistorySuccessfulEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 112;
    
    public ClearHistorySuccessfulEvent() {
        super(REQUEST_CODE);
    }
}
