package com.odauday.ui.propertydetail.common;

import android.os.Parcelable;

/**
 * Created by infamouSs on 5/17/18.
 */
public class ItemSelectModel<DATA extends Parcelable> {
    
    public static final int PHONE = 0;
    public static final int EMAIL = 1;
    
    private String text;
    private int type;
    private DATA data;
    
    public ItemSelectModel() {
    
    }
    
    public ItemSelectModel(String text, int type, DATA data) {
        this.text = text;
        this.type = type;
        this.data = data;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public DATA getData() {
        return data;
    }
    
    public void setData(DATA data) {
        this.data = data;
    }
}
