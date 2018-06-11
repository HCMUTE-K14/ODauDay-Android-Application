package com.odauday.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.odauday.R;
import com.odauday.utils.NumberUtils;

/**
 * Created by infamouSs on 5/18/18.
 */
public class AspectRatioImageView extends ImageView {
    
    private float mRatio = 1.0f;
    private float mScaleFactor = 1.0f;
    
    public AspectRatioImageView(Context context) {
        super(context);
    }
    
    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context
            .obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        int widthRatio = a.getInt(R.styleable.AspectRatioImageView_widthRatio, 3);
        int heightRatio = a.getInt(R.styleable.AspectRatioImageView_heightRatio, 2);
        this.mScaleFactor = a.getFloat(R.styleable.AspectRatioImageView_scaleFactor, 1.0f);
        this.mRatio = ((float) widthRatio) / ((float) heightRatio);
        a.recycle();
    }
    
    public void setAspectRatio(int w, int h) {
        this.mRatio = ((float) w) / ((float) h);
        invalidate();
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
    
    
}
