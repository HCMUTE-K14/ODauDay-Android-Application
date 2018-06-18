package com.odauday.ui.propertydetail.common;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 6/11/18.
 */
public class OnChangeNoteEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 127;
    
    public OnChangeNoteEvent() {
        super(REQUEST_CODE);
    }
}
