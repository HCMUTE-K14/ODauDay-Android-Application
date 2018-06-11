package com.odauday.ui.propertydetail.rowdetails.vital;

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
public class VitalDetailRow extends BaseRowDetail<PropertyDetail, VitalDetailViewHolder> {
    
    private PropertyDetail mData;
    
    public VitalDetailRow() {
    
    }
    
    @Override
    public PropertyDetail getData() {
        return mData;
    }
    
    @Override
    public void setData(PropertyDetail data) {
        mData = data;
    }
    
    
    public VitalDetailViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_vital_property_detail, parent, false);
        return new VitalDetailViewHolder(view);
    }
    
    public StageRow getStageRow() {
        return StageRow.VITAL_ROW;
    }
}
