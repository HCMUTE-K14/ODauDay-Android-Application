package com.odauday.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.odauday.R;

/**
 * Created by infamouSs on 5/31/18.
 */
public class TextViewWithLeftIconView extends LinearLayout {
    
    ImageView mIcon;
    TextView mText;
    
    public TextViewWithLeftIconView(Context context) {
        super(context);
        init(context);
    }
    
    public TextViewWithLeftIconView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public TextViewWithLeftIconView(Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_text_view_with_left_drawable, this, true);
        
        mIcon = rootView.findViewById(R.id.icon);
        mText = rootView.findViewById(R.id.text);
    }
    
    public ImageView getIcon() {
        return mIcon;
    }
    
    public TextView getText() {
        return mText;
    }
}
