package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.api.EndPoint;
import com.odauday.utils.ImageLoader;

/**
 * Created by infamouSs on 5/7/18.
 */
public class GalleryDetailViewHolder {
    
    private OnClickImage mOnClickImage;
    
    public GalleryDetailViewHolder(OnClickImage onClickImage) {
        this.mOnClickImage = onClickImage;
    }
    
    
    public void update(GalleryDetailRow galleryDetailRow) {
        if (galleryDetailRow.getData() == null) {
            return;
        }
        
        if (galleryDetailRow.getData().getImages().isEmpty()) {
            return;
        }
        int countImages = galleryDetailRow.getData().getImages().size() - 1;
        DisplayMode displayMode = getDisplayMode(countImages);
        ViewGroup parentView = galleryDetailRow.getParentView();
        
        setImageMain(galleryDetailRow);
        
        ImageViewHolder imageViewHolder;
        switch (displayMode.getIndex()) {
            case 1:
                imageViewHolder = new SingleImageViewHolder(parentView, mOnClickImage);
                break;
            case 2:
                imageViewHolder = new DoubleImageViewHolder(parentView, mOnClickImage);
                break;
            case 3:
                imageViewHolder = new TripleImageViewHolder(parentView, mOnClickImage);
                break;
            default:
                return;
        }
        
        imageViewHolder.setImages(galleryDetailRow.getData().getImages());
        
        galleryDetailRow.getMainImageView().setVisibility(View.VISIBLE);
    }
    
    private void setImageMain(GalleryDetailRow galleryDetailRow) {
        galleryDetailRow.getMainImageView().getLayoutParams().height = (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f,
                galleryDetailRow.getResources().getDisplayMetrics());
        galleryDetailRow.getMainImageView().requestLayout();
        
        galleryDetailRow.getMainImageView().setOnClickListener(view -> {
            if (mOnClickImage != null) {
                mOnClickImage.onClickImage(0, galleryDetailRow.getData().getImages());
            }
        });
        ImageLoader
            .load(galleryDetailRow.getMainImageView(), EndPoint.BASE_URL +
                                                       galleryDetailRow.getData().getImages()
                                                           .get(0).getUrl());
        
    }
    
    private DisplayMode getDisplayMode(int countImages) {
        switch (countImages) {
            case 0:
                return DisplayMode.NONE;
            case 1:
                return DisplayMode.SINGLE;
            case 2:
                return DisplayMode.DOUBLE;
            default:
                return DisplayMode.TRIPLE;
        }
    }
}
