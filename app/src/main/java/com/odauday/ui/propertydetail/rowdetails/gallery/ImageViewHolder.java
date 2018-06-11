package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.odauday.api.EndPoint;
import com.odauday.model.Image;
import com.odauday.utils.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/7/18.
 */
abstract class ImageViewHolder {
    
    View mContainer;
    DisplayMode mDisplayMode;
    List<Image> mImages;
    OnClickImage mOnClickImage;
    
    public ImageViewHolder(ViewGroup container,
        DisplayMode displayMode, OnClickImage onClickImage) {
        this.mDisplayMode = displayMode;
        this.mContainer = displayMode.showView(container);
        this.mOnClickImage = onClickImage;
    }
    
    public void setImages(List<Image> images) {
        if (mContainer == null) {
            return;
        }
        if (mImages == null) {
            mImages = new ArrayList<>();
        }
        if (images == null) {
            return;
        }
        mImages.addAll(images);
        List<Image> imagesListExpectFirstItem = images.subList(1, images.size());
        
        if (!imagesListExpectFirstItem.isEmpty()) {
            int sizeEntry = mDisplayMode.getEntriesToDisplay().size();
            for (int i = 0; i < sizeEntry; i++) {
                loadImageFromGalleryImageEntryByPosition(imagesListExpectFirstItem, i);
            }
            if (imagesListExpectFirstItem.size() >
                DisplayMode.TRIPLE.getEntriesToDisplay().size()) {
                updateSubThirdImage();
            }
        }
    }
    
    
    protected void updateSubThirdImage() {
    
    }
    
    protected void loadImageFromGalleryImageEntryByPosition(List<Image> data, int pos) {
        GalleryImageEntryHolder entryHolder = mDisplayMode
            .getEntriesToDisplay()
            .get(pos);
        ImageView imageView = mContainer.findViewById(entryHolder.getViewId());
        imageView.setOnClickListener(view -> {
            if (mOnClickImage != null) {
                mOnClickImage.onClickImage(pos + 1);
            }
        });
        ImageLoader.load(imageView, EndPoint.BASE_URL + data.get(pos).getUrl());
    }
}
