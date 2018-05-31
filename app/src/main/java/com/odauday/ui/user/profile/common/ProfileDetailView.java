package com.odauday.ui.user.profile.common;

import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/30/18.
 */
@BindingMethods(
    {
        @BindingMethod(type = ProfileDetailView.class,
            attribute = "txtHeader", method = "setHeader"),
        @BindingMethod(type = ProfileDetailView.class,
            attribute = "txtValue", method = "setValue"),
        @BindingMethod(type = ProfileDetailView.class,
            attribute = "txtHeaderWithoutColon", method = "setHeaderWithoutColon")
    }
)
public class ProfileDetailView extends LinearLayout {
    
    TextView mHeader;
    TextView mText;
    TextView mSubText;
    RelativeLayout mContainer;
    
    OnClickDetail mOnClickDetail;
    
    public ProfileDetailView(Context context) {
        super(context);
        init(context);
    }
    
    public ProfileDetailView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public ProfileDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_item_detail, this, true);
        
        mHeader = rootView.findViewById(R.id.header);
        mText = rootView.findViewById(R.id.text);
        mSubText = rootView.findViewById(R.id.sub_text);
        mContainer = rootView.findViewById(R.id.container);
    }
    
    public void setHeader(String header) {
        String str = header + ":";
        mHeader.setText(str);
    }
    
    public void setHeader(int header) {
        String str = getContext().getString(header) + ":";
        mHeader.setText(str);
    }
    
    public void setHeaderWithoutColon(int header) {
        mHeader.setText(header);
    }
    
    public void setHeaderWithoutColon(String header) {
        mHeader.setText(header);
    }
    
    public void setValue(String title) {
        if (TextUtils.isEmpty(title)) {
            mText.setText(R.string.n_a);
            return;
        }
        mText.setText(title);
    }
    
    public void setValue(int title) {
        if (title <= 0) {
            mText.setText(R.string.n_a);
            return;
        }
        mText.setText(title);
    }
    
    public void setSubText(int value) {
        if (value <= 0) {
            mSubText.setText(R.string.n_a);
            return;
        }
        mSubText.setText(value);
    }
    
    public void setSubText(String str) {
        if (TextUtils.isEmpty(str)) {
            mSubText.setText(R.string.n_a);
            return;
        }
        mSubText.setText(str);
    }
    
    
    public String getValue() {
        return mText.getText().toString();
    }
    
    public void setListener(OnClickDetail onClickDetail) {
        mOnClickDetail = onClickDetail;
        mContainer.setOnClickListener(view -> {
            if (mOnClickDetail != null) {
                mOnClickDetail.onClickDetail();
            }
        });
    }
    
    public TextView getSubText() {
        return mSubText;
    }
    
    public interface OnClickDetail {
        
        void onClickDetail();
    }
}
