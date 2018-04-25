package com.odauday.ui.addeditproperty.step1;

import android.text.InputType;
import com.odauday.R;

/**
 * Created by infamouSs on 4/25/18.
 */
public enum PhoneAndEmailEnum {
    PHONE(InputType.TYPE_CLASS_PHONE, R.string.txt_phone_number, R.drawable.ic_phone, 1),
    EMAIL(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
          InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, R.string.txt_email, R.drawable.ic_email, 2);
    
    int mTypeInput;
    int mText;
    int mImage;
    int mId;
    
    private PhoneAndEmailEnum(int typeInput, int stringResourceId, int drawableResourceId, int id) {
        this.mTypeInput = typeInput;
        this.mText = stringResourceId;
        this.mImage = drawableResourceId;
        this.mId = id;
    }
    
    public int getId() {
        return mId;
    }
}
