package com.odauday.ui.propertydetail.rowdetails;

import android.view.ViewGroup;

/**
 * Created by infamouSs on 5/7/18.
 */
public abstract class BaseRowDetail<T, ViewHolder extends BaseRowViewHolder> {
    
    
    public abstract T getData();
    
    public abstract void setData(T data);
    
    public int getType() {
        return getClass().hashCode();
    }
    
    public abstract ViewHolder createViewHolder(ViewGroup parent);
}
