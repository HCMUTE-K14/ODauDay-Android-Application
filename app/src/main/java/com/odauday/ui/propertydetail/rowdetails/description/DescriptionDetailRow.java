package com.odauday.ui.propertydetail.rowdetails.description;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DescriptionDetailRow extends
                                  BaseRowDetail<PropertyDetail, DescriptionDetailViewHolder> {
    
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
    public DescriptionDetailViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_description_property_detail, parent, false);
        return new DescriptionDetailViewHolder(view);
    }
    
    @Override
    public StageRow getStageRow() {
        return StageRow.DESCRIPTION_ROW;
    }
}
