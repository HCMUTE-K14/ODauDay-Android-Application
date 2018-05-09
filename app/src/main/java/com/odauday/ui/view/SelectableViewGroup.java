package com.odauday.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/8/18.
 */
public class SelectableViewGroup extends LinearLayout implements OnClickListener {
    
    private void bindClickHandler() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setOnClickListener(this);
        }
    }
    
    @Override
    public void onClick(View v) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setSelected(false);
        }
        v.setSelected(true);
    }
    
    public int getSelectedIndex() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).isSelected()) {
                return i;
            }
        }
        return 0;
    }
    
    public void setSelected(int index) {
        onClick(getChildAt(index));
    }
    
    public SelectableViewGroup(Context context) {
        super(context);
        init();
    }
    
    public SelectableViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SelectableViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        bindClickHandler();
        Timber.d(getChildCount() + "");
    }
    
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() == 0) {
            child.setSelected(true);
        }
        child.setOnClickListener(this);
        super.addView(child, index, params);
    }
}