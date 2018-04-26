package com.odauday.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.ui.addeditproperty.step1.PhoneAndEmailEnum;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.ValidationHelper;

/**
 * Created by infamouSs on 4/24/18.
 */
public class AddRowItemView extends LinearLayout {
    
    
    public AddRowItemView(Context context) {
        super(context);
        init(context);
    }
    
    public AddRowItemView(Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public AddRowItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_add_row_view, this, true);
        
    }
    
    public ImageView getPlusOrMinusBtn() {
        return getRootView().findViewById(R.id.plus_or_minus_btn);
    }
    
    public boolean isValid(int type) {
        if (type == PhoneAndEmailEnum.EMAIL.getId()) {
            
            String email = getTextView().getText().toString();
            return !ValidationHelper.isEmail(email);
        } else if (type == PhoneAndEmailEnum.PHONE.getId()) {
            String phone = getTextView().getText().toString();
            return !ValidationHelper.isPhoneNumber(phone);
        } else {
            return false;
        }
    }
    
    public ImageView getImageView() {
        return getRootView().findViewById(R.id.image);
    }
    
    public EditText getTextView() {
        return getRootView().findViewById(R.id.txt_title);
    }
    
    public void setTypeText(int type) {
        getTextView().setInputType(type);
    }
    
    public void setImage(Object url) {
        ImageLoader.load(getImageView(), url);
    }
    
    public void setText(String text) {
        getTextView().setText(text);
    }
    
    public void setText(int text) {
        getTextView().setText(text);
    }
    
    public void setPlusOrMinusButtonTag(int tag) {
        getPlusOrMinusBtn().setTag(tag);
    }
}
