package com.odauday.ui.galleryviewer;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.base.BaseActivity;
import com.odauday.ui.view.CircleIndicator;

/**
 * Created by infamouSs on 5/8/18.
 */
public class GalleryViewerActivity extends BaseActivity implements OnPageChangeListener {
    
    
    private int mSelectedPosition;
    
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery_viewer;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        
        PropertyDetail propertyDetail;
        if (getIntent() != null) {
            propertyDetail = getIntent()
                .getParcelableExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL);
            mSelectedPosition = getIntent()
                .getIntExtra(Constants.INTENT_EXTRA_SELECTED_IMAGE_POSITION, 0);
        } else {
            throw new IllegalArgumentException("Need INTENT_EXTRA_PROPERTY_DETAIL");
        }
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        if (propertyDetail != null && getSupportActionBar() != null) {
            String title = propertyDetail.getAddress();
            getSupportActionBar().setTitle(title);
            
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            
            getSupportActionBar().show();
        }
        
        CircleIndicator circleIndicator = findViewById(R.id.indicator);
        
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.addOnPageChangeListener(this);
        if (propertyDetail != null && propertyDetail.getImages() != null) {
            GalleryViewAdapter galleryViewAdapter = new GalleryViewAdapter(
                getSupportFragmentManager(),
                propertyDetail.getImages());
            viewPager.setAdapter(galleryViewAdapter);
            viewPager.setCurrentItem(mSelectedPosition);
        }
        circleIndicator.setViewPager(viewPager);
        hideStatusBar();
    }
    
    
    private void hideStatusBar() {
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        } else {
            getWindow().getDecorView().setSystemUiVisibility(R.id.toolbar);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    
    }
    
    @Override
    public void onPageSelected(int position) {
        mSelectedPosition = position;
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
    
    }
}
