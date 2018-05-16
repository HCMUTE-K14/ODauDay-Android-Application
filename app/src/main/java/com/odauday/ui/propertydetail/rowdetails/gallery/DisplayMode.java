package com.odauday.ui.propertydetail.rowdetails.gallery;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import com.odauday.R;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by infamouSs on 5/7/18.
 */
enum DisplayMode {
    NONE(-1, -1) {
        @Override
        public List<GalleryImageEntryHolder> getEntriesToDisplay() {
            return null;
        }
    },
    SINGLE(R.id.single_image_stub, 1) {
        @Override
        public List<GalleryImageEntryHolder> getEntriesToDisplay() {
            return Collections.singletonList(GalleryImageEntryHolder.SUB_IMAGE_1);
        }
    },
    DOUBLE(R.id.double_image_stub, 2) {
        @Override
        public List<GalleryImageEntryHolder> getEntriesToDisplay() {
            return Arrays
                .asList(GalleryImageEntryHolder.SUB_IMAGE_1,
                    GalleryImageEntryHolder.SUB_IMAGE_2);
        }
    },
    TRIPLE(R.id.triple_image_stub, 3) {
        @Override
        public List<GalleryImageEntryHolder> getEntriesToDisplay() {
            return Arrays
                .asList(GalleryImageEntryHolder.SUB_IMAGE_1, GalleryImageEntryHolder.SUB_IMAGE_2,
                    GalleryImageEntryHolder.SUB_IMAGE_3);
        }
    };
    
    
    private int viewId;
    private int index;
    
    abstract public List<GalleryImageEntryHolder> getEntriesToDisplay();
    
    DisplayMode(int viewId, int index) {
        this.index = index;
        this.viewId = viewId;
    }
    
    public int getViewId() {
        return viewId;
    }
    
    public int getIndex() {
        return index;
    }
    
    public View showView(ViewGroup parent) {
        
        if (parent != null) {
            View view = parent.findViewById(viewId);
            try {
                if (((ViewStub) view).getParent() != null) {
                    return ((ViewStub) view).inflate();
                }
                return view;
            } catch (Exception ex) {
                return view;
            }
        }
        return null;
    }
}
