package com.odauday.ui.search.common.event;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 6/2/18.
 */
public class OnShowSortDialogEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 115;
    
    public OnShowSortDialogEvent() {
        super(REQUEST_CODE);
    }
}
