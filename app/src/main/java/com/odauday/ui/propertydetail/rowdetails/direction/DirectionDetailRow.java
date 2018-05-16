package com.odauday.ui.propertydetail.rowdetails.direction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.data.DirectionRepository;
import com.odauday.data.local.cache.DirectionsPreference;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DirectionDetailRow extends BaseRowDetail<PropertyDetail, DirectionDetailViewHolder> {
    
    
    private PropertyDetail mData;
    
    private DirectionRepository mDirectionRepository;
    private DirectionsPreference mDirectionsPreference;
    
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
    
    public DirectionRepository getDirectionRepository() {
        return mDirectionRepository;
    }
    
    public DirectionsPreference getDirectionsPreference() {
        return mDirectionsPreference;
    }
    
    public void setDirectionRepository(DirectionRepository directionRepository) {
        mDirectionRepository = directionRepository;
    }
    
    public void setDirectionsPreference(
        DirectionsPreference directionsPreference) {
        mDirectionsPreference = directionsPreference;
    }
    
    @Override
    public StageRow getStageRow() {
        return StageRow.DIRECTION_ROW;
    }
}
