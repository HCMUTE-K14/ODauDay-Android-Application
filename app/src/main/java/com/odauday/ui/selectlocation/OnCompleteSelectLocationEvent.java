package com.odauday.ui.selectlocation;

import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/25/18.
 */
public class OnCompleteSelectLocationEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 105;
    
    private AddressAndLocationObject mData;
    
    public OnCompleteSelectLocationEvent(AddressAndLocationObject object) {
        super(REQUEST_CODE);
        this.mData = object;
    }
    
    public AddressAndLocationObject getData() {
        return mData;
    }
}
