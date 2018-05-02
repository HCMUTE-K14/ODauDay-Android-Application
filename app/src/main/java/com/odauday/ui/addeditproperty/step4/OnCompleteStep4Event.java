package com.odauday.ui.addeditproperty.step4;

import com.odauday.model.MyProperty;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/25/18.
 */
public class OnCompleteStep4Event extends BaseEvent {
    public static final int REQUEST_CODE = 112;
    
    private MyProperty mProperty;
    public OnCompleteStep4Event(MyProperty myProperty) {
        super(REQUEST_CODE);
        mProperty = myProperty;
    }
    
    public MyProperty getData() {
        return mProperty;
    }
}
