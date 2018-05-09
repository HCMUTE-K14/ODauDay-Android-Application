package com.odauday.ui.propertydetail.rowdetails.bedbathpark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/8/18.
 */
public class BedBathParkingRow extends BaseRowDetail<PropertyDetail, BedBathParkingViewHolder> {
    
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
    public BedBathParkingViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_bed_bath_parking_property_detail, parent, false);
        return new BedBathParkingViewHolder(view);
    }
}
