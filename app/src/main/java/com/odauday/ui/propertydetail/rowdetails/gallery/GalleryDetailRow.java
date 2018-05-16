package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.model.Image;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import java.util.List;
import timber.log.Timber;

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
        Timber.d(propertyDetail.getImages().toString());
        mGalleryDetailViewHolder.update(this);
    }
    
    public void setData(PropertyDetail data) {
        mData = data;
    }
    
    @Override
    public void onClickImage(int pos, List<Image> images) {
        Timber.d("on click iamge " + pos);
    }
}
