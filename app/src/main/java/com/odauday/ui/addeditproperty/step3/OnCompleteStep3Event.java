package com.odauday.ui.addeditproperty.step3;

import com.odauday.model.MyProperty;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/25/18.
 */
public class OnCompleteStep3Event extends BaseEvent {
    
    public static final int REQUEST_CODE = 111;
    
    private MyProperty mProperty;
    
    public OnCompleteStep3Event(MyProperty myProperty) {
        super(REQUEST_CODE);
        this.mProperty = myProperty;
    }
    
    public MyProperty getData() {
        return mProperty;
    }
}
