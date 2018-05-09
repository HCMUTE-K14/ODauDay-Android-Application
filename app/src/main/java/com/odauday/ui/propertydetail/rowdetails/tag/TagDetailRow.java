package com.odauday.ui.propertydetail.rowdetails.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/8/18.
 */
public class TagDetailRow extends BaseRowDetail<PropertyDetail, TagDetailViewHolder> {
    
    
    private PropertyDetail mData;
    
    @Override
    public PropertyDetail getData() {
        return mData;
    }
    
    @Override
    public void setData(PropertyDetail data) {
        mData = data;
    }
    
    @Override
    public TagDetailViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_tag_property_detail, parent, false);
        return new TagDetailViewHolder(view);
    }
}
