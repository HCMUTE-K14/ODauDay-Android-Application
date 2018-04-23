package com.odauday.ui.base;

/**
 * Created by infamouSs on 4/15/18.
 */
public abstract class BaseEvent {
    
    private final int requestCode;
    
    public BaseEvent(int requestCode) {
        this.requestCode = requestCode;
    }
    
    public int getRequestCode() {
        return requestCode;
    }
}
