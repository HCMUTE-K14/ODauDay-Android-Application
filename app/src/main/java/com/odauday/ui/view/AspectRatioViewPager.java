package com.odauday.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.odauday.R;
import com.odauday.utils.NumberUtils;

/**
 * Created by infamouSs on 6/1/18.
 */
public class AspectRatioViewPager extends ViewPager {
    
    public static final int MIN_SWIPABLE_VIEWS = 2;
    // private OnInterceptTouchEventListener mInterceptTouchEventListener;
    private float mRatio = 1.0f;
    private float mScaleFactor = 1.0f;
    
    public AspectRatioViewPager(@NonNull Context context) {
        super(context);
    }
    
    public AspectRatioViewPager(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioViewPager);
        int widthRatio = a
            .getInt(R.styleable.AspectRatioViewPager_widthRatio_viewpager, 3);
        int heightRatio = a
            .getInt(R.styleable.AspectRatioViewPager_heightRatio_viewpager, 2);
        this.mScaleFactor = a
            .getFloat(R.styleable.AspectRatioViewPager_scaleFactor_viewpager, 1.0f);
        this.mRatio = ((float) widthRatio) / ((float) heightRatio);
        a.recycle();
    }
    
    public void setScaleFactor(float scaleFactor) {
        this.mScaleFactor = scaleFactor;
    }
    
    public float getScaleFactor() {
        return this.mScaleFactor;
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) != 0) {
            int width = getMeasuredWidth();
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec
                .makeMeasureSpec(
                    NumberUtils.getHeightWithAspectRatio(width, this.mRatio, this.mScaleFactor),
                    MeasureSpec.EXACTLY));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() < 2) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
    
    //    public boolean onInterceptTouchEvent(MotionEvent ev) {
    //        if (getChildCount() < 2) {
    //            return false;
    //        }
    //        boolean isViewSettling = super.onInterceptTouchEvent(ev);
    //        if (this.mInterceptTouchEventListener == null) {
    //            return isViewSettling;
    //        }
    //        this.mInterceptTouchEventListener.onTouchEvent(ev, isViewSettling);
    //        return isViewSettling;
    //    }
}
