package com.odauday.ui.alert.service;

/**
 * Created by kunsubin on 6/8/2018.
 */

public class NotificationEvent<T> {
    private int mCode;
    private T mData;
    
    public NotificationEvent(int code, T data) {
        mCode = code;
        mData = data;
    }
    
    public int getCode() {
        return mCode;
    }
    
    public void setCode(int code) {
        mCode = code;
    }
    
    public T getData() {
        return mData;
    }
    
    public void setData(T data) {
        mData = data;
    }
}
