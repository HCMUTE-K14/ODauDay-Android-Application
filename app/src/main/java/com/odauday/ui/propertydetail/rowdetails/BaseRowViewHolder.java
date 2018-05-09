package com.odauday.ui.propertydetail.rowdetails;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.odauday.utils.ObjectUtils;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/7/18.
 */
public abstract class BaseRowViewHolder<ROW extends BaseRowDetail> extends
                                                                   ViewHolder {
    
    protected abstract void update(ROW row);
    
    public BaseRowViewHolder(View itemView) {
        super(itemView);
    }
    
    public void bind(ROW row) {
        update(row);
    }
    
    
    public abstract void unbind();
    
    protected void updateTextView(CharSequence text, TextView view) {
        boolean showText;
        int i = View.VISIBLE;
        showText = !TextUtils.isEmpty(text.toString());
        if (!showText) {
            i = View.GONE;
        }
        
        view.setVisibility(i);
        if (showText && !ObjectUtils.equals(text, view.getText())) {
            view.setText(text);
        }
    }
    
    protected void updateTextView(TextView view, String text) {
        updateTextView(text == null ? null : text.trim(), view);
    }
}
