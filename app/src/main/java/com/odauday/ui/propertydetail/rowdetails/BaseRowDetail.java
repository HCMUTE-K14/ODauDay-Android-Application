package com.odauday.ui.propertydetail.rowdetails;

import android.view.ViewGroup;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 5/7/18.
 */
public abstract class BaseRowDetail<T, ViewHolder extends BaseRowViewHolder> {
    
    
    protected int mPosition;
    
    public abstract T getData();
    
    public abstract void setData(T data);
    
    public int getType() {
        return getClass().hashCode();
    }
    
    public abstract ViewHolder createViewHolder(ViewGroup parent);
    
    public abstract StageRow getStageRow();
    
    public void setPosition(int position) {
        this.mPosition = position;
    }
    
    public int getPosition() {
        return mPosition;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseRowDetail<?, ?> that = (BaseRowDetail<?, ?>) o;
        return getStageRow() == that.getStageRow();
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(getStageRow());
    }
}
