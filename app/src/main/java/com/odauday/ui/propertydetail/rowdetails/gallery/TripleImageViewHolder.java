package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.view.ViewGroup;
import android.widget.TextView;
import com.odauday.R;

/**
 * Created by infamouSs on 5/7/18.
 */
public class TripleImageViewHolder extends ImageViewHolder {
    
    private static final float ALPHA_HALF_TRANSPARENT = 0.5f;
    private static final float ALPHA_NORMAL = 1f;
    
    public TripleImageViewHolder(ViewGroup container, OnClickImage onClickImage) {
        super(container, DisplayMode.TRIPLE, onClickImage);
    }
    
    @Override
    protected void updateSubThirdImage() {
        super.updateSubThirdImage();
        int moreImageCount = mImages.size() - DisplayMode.TRIPLE.getEntriesToDisplay().size();
        
        if (moreImageCount > 0) {
            setMoreImageText(moreImageCount);
            setAlphaImageView(ALPHA_HALF_TRANSPARENT);
        } else {
            setAlphaImageView(ALPHA_NORMAL);
        }
        
    }
    
    private void setMoreImageText(int moreImageCount) {
        TextView textView = mContainer
            .findViewById(GalleryImageEntryHolder.MORE_TEXT.getViewId());
        String txt = mContainer.getContext()
            .getString(R.string.txt_view_more_image, moreImageCount);
        textView.setText(txt);
    }
    
    private void setAlphaImageView(float value) {
        mContainer.findViewById(GalleryImageEntryHolder.SUB_IMAGE_3.getViewId())
            .setAlpha(value);
    }
}
