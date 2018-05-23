package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.galleryviewer.GalleryViewerActivity;
import com.odauday.ui.propertydetail.StageRow;

/**
 * Created by infamouSs on 5/7/18.
 */
public class GalleryDetailRow extends LinearLayout implements
                                                   OnClickImage {
    
    private GalleryDetailViewHolder mGalleryDetailViewHolder;
    
    private ViewGroup mParentView;
    
    private PropertyDetail mData;
    
    public GalleryDetailRow(Context context) {
        super(context);
        init(context);
    }
    
    public GalleryDetailRow(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        
    }
    
    public GalleryDetailRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        
    }
    
    public StageRow getStageRow() {
        return StageRow.GALLERY_ROW;
    }
    
    public void init(Context context) {
        setData(null);
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        
        View rootView = (ViewGroup) inflater
            .inflate(R.layout.row_gallery_property_detail, this, true);
        
        mParentView = rootView.findViewById(R.id.gallery_container);
        mGalleryDetailViewHolder = new GalleryDetailViewHolder(this);
    }
    
    public ImageView getMainImageView() {
        return mParentView.findViewById(GalleryImageEntryHolder.MAIN_IMAGE.getViewId());
    }
    
    public ViewGroup getParentView() {
        return mParentView == null ?
            getRootView().findViewById(R.id.gallery_container)
            : mParentView;
    }
    
    public PropertyDetail getData() {
        return mData;
    }
    
    public void bind(PropertyDetail propertyDetail) {
        setData(propertyDetail);
        mGalleryDetailViewHolder.update(this);
    }
    
    public void setData(PropertyDetail data) {
        mData = data;
    }
    
    @Override
    public void onClickImage(int pos) {
        Intent intent = new Intent(getContext(), GalleryViewerActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, getData());
        intent.putExtra(Constants.INTENT_EXTRA_SELECTED_IMAGE_POSITION, pos);
        
        getContext().startActivity(intent);
    }
}
