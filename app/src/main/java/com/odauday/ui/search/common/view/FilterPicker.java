package com.odauday.ui.search.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.ui.view.wheelview.OnWheelScrollListener;
import com.odauday.ui.view.wheelview.WheelView;
import com.odauday.ui.view.wheelview.adapters.WheelViewAdapter;

/**
 * Created by infamouSs on 4/2/18.
 */

public class FilterPicker extends FrameLayout implements OnClickListener {
    
    private WheelView mFilterPicker;
    
    public FilterPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public FilterPicker(Context context) {
        super(context);
        init(context);
    }
    
    public FilterPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public WheelViewAdapter getViewAdapter() {
        return this.mFilterPicker.getViewAdapter();
    }
    
    public void setViewAdapter(WheelViewAdapter adapter) {
        this.mFilterPicker.setViewAdapter(adapter);
    }
    
    public int getCurrentItem() {
        return this.mFilterPicker.getCurrentItem();
    }
    
    public void setCurrentItem(int index) {
        this.mFilterPicker.setCurrentItem(index);
    }
    
    public void setCurrentItem(int index, boolean isAnimated) {
        this.mFilterPicker.setCurrentItem(index, isAnimated);
    }
    
    public void addScrollingListener(OnWheelScrollListener listener) {
        this.mFilterPicker.addScrollingListener(listener);
    }
    
    public void removeScrollingListener(OnWheelScrollListener listener) {
        this.mFilterPicker.removeScrollingListener(listener);
    }
    
    public void setId(int id) {
        this.mFilterPicker.setId(id);
    }
    
    public void setHeading(String text) {
        ((TextView) findViewById(R.id.txt_header)).setText(text);
    }
    
    public void onClick(View v) {
        int currentIndex = this.mFilterPicker.getCurrentItem();
        switch (v.getId()) {
            case R.id.img_arrow_up:
                if (currentIndex > 0) {
                    this.mFilterPicker.setCurrentItem(currentIndex - 1);
                    return;
                }
                return;
            case R.id.img_arrow_down:
                if (currentIndex < this.mFilterPicker.getViewAdapter().getItemsCount()) {
                    this.mFilterPicker.setCurrentItem(currentIndex + 1);
                    return;
                }
                return;
            default:
                return;
        }
    }
    
    private void init(Context context) {
        View v = View.inflate(context, R.layout.layout_filter_picker, null);
        this.mFilterPicker = (WheelView) v.findViewById(R.id.wheel_view);
        v.findViewById(R.id.img_arrow_up).setOnClickListener(this);
        v.findViewById(R.id.img_arrow_down).setOnClickListener(this);
        this.mFilterPicker.setCacheColorHint(-1);
        this.mFilterPicker.setSelectorDrawable(
            getResources().getDrawable(R.drawable.ic_selector_wheel_view));
        addView(v);
    }
}
