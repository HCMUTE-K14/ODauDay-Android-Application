package com.odauday.ui.propertydetail.rowdetails.enquiry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/17/18.
 */
public class EnquiryDetailRow extends BaseRowDetail<PropertyDetail, EnquiryViewHolder> {
    
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
    public EnquiryViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_email_form_property_detail, parent, false);
        return new EnquiryViewHolder(view);
    }
    
    @Override
    public StageRow getStageRow() {
        return StageRow.ENQUIRY_ROW;
    }
}
