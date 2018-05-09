package com.odauday.ui.propertydetail.rowdetails.direction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DirectionDetailRow extends BaseRowDetail<PropertyDetail, DirectionDetailViewHolder> {
    
    
    private PropertyDetail mData;
    
    
    private boolean mIsShowDirectionForm = false;
    
    public boolean isShowDirectionForm() {
        return mIsShowDirectionForm;
    }
    
    public void setShowDirectionForm(boolean showDirectionForm) {
        mIsShowDirectionForm = showDirectionForm;
    }
    
    @Override
    public PropertyDetail getData() {
        return mData;
    }
    
    @Override
    public void setData(PropertyDetail data) {
        mData = data;
    }
    
    @Override
    public DirectionDetailViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_direction_property_detail, parent, false);
        return new DirectionDetailViewHolder(view, true);
    }
}
