package com.odauday.ui.propertydetail.rowdetails.gallery;


import com.odauday.R;

/**
 * Created by infamouSs on 5/7/18.
 */
enum GalleryImageEntryHolder {
    MAIN_IMAGE(R.id.main_image, -1),
    SUB_IMAGE_1(R.id.image_1, 0),
    SUB_IMAGE_2(R.id.image_2, 1),
    SUB_IMAGE_3(R.id.image_3, 2),
    MORE_TEXT(R.id.view_more, 4);
    
    private int viewId;
    private int pos;
    
    GalleryImageEntryHolder(int viewId, int pos) {
        this.viewId = viewId;
        this.pos = pos;
    }
    
    public int getViewId() {
        return viewId;
    }
    
    public int getPos() {
        return pos;
    }
}
