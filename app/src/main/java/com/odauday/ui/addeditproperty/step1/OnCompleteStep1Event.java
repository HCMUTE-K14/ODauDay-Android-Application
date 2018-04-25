package com.odauday.ui.addeditproperty.step1;

import com.odauday.model.MyProperty;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/25/18.
 */
public class OnCompleteStep1Event extends BaseEvent {
    
    public static final int REQUEST_CODE = 109;
    
    private MyProperty mProperty;
    
    public OnCompleteStep1Event(MyProperty myProperty) {
        super(REQUEST_CODE);
        this.mProperty = myProperty;
    }
    
    public MyProperty getData() {
        return mProperty;
    }
}
