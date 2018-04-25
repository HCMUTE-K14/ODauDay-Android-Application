package com.odauday.ui.addeditproperty.step2;

import com.odauday.model.MyProperty;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/25/18.
 */
public class OnCompleteStep2Event extends BaseEvent {
    
    public static final int REQUEST_CODE = 110;
    
    private MyProperty mProperty;
    
    public OnCompleteStep2Event(MyProperty myProperty) {
        super(REQUEST_CODE);
        this.mProperty = myProperty;
    }
    
    public MyProperty getData() {
        return mProperty;
    }
}
