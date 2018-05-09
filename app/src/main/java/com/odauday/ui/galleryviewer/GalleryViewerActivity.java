package com.odauday.ui.galleryviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.odauday.R;
import com.odauday.ui.base.BaseActivity;

/**
 * Created by infamouSs on 5/8/18.
 */
public class GalleryViewerActivity extends BaseActivity {
    
    
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery_viewer;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        
    }
}
