package com.odauday.ui.propertydetail.rowdetails.similar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.data.SimilarPropertyRepository;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarPropertyRow extends BaseRowDetail<PropertyDetail, SimilarPropertyViewHolder> {
    
    private PropertyDetail mPropertyDetail;
    
    private SimilarPropertyRepository mSimilarPropertyRepository;
    
    @Override
    public PropertyDetail getData() {
        return mPropertyDetail;
    }
    
    @Override
    public void setData(PropertyDetail data) {
        mPropertyDetail = data;
    }
    
    @Override
    public SimilarPropertyViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_similar_property_root, parent, false);
        return new SimilarPropertyViewHolder(view);
    }
    
    public void setSimilarPropertyRepository(
        SimilarPropertyRepository similarPropertyRepository) {
        mSimilarPropertyRepository = similarPropertyRepository;
    }
    
    public SimilarPropertyRepository getSimilarPropertyRepository() {
        return mSimilarPropertyRepository;
    }
    
    @Override
    public StageRow getStageRow() {
        return StageRow.SIMILAR_PROPERTY;
    }
}
