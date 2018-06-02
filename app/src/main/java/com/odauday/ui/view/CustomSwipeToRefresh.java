package com.odauday.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.odauday.R;

/**
 * Created by infamouSs on 6/1/18.
 */
public class CustomSwipeToRefresh extends SwipeRefreshLayout {
    
    private float mPrevX;
    private int mTouchSlop;
    
    public CustomSwipeToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        
        int height = getStatusBarHeight(context);
        setProgressViewOffset(false, height, (height * 3) / 2);
    }
    
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mPrevX = MotionEvent.obtain(event).getX();
                break;
            case 2:
                if (Math.abs(event.getX() - this.mPrevX) > ((float) this.mTouchSlop)) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
    
    int getStatusBarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data,
            context.getResources().getDisplayMetrics());
    }
}
