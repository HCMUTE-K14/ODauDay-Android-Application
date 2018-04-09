package com.odauday.ui.search.common;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.odauday.R;
import com.odauday.model.Tag;
import com.odauday.ui.search.navigation.FilterOption;
import com.odauday.ui.search.navigation.PropertyType;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 4/7/18.
 */

public class TextAndMoreTextValue implements Parcelable {
    
    public static final Creator<TextAndMoreTextValue> CREATOR = new Creator<TextAndMoreTextValue>() {
        @Override
        public TextAndMoreTextValue createFromParcel(Parcel in) {
            return new TextAndMoreTextValue(in);
        }
        
        @Override
        public TextAndMoreTextValue[] newArray(int size) {
            return new TextAndMoreTextValue[size];
        }
    };
    private String text;
    private String moreText;
    
    public TextAndMoreTextValue(String text, String moreText) {
        this.text = text;
        this.moreText = moreText;
    }
    
    protected TextAndMoreTextValue(Parcel in) {
        text = in.readString();
        moreText = in.readString();
    }
    
    @SuppressWarnings("unchecked")
    public static TextAndMoreTextValue build(Context context, FilterOption option,
              List lists) {
        String text;
        String moreText = "";
        
        int size = lists.size();
        
        if (size == 0) {
            text = context.getString(R.string.txt_any);
            
            return new TextAndMoreTextValue(text, moreText);
        }
        
        if (size <= 2) {
            text = buildText(context, option, lists);
        } else {
            text = buildText(context, option, lists);
            moreText = buildMoreText(context, option, lists);
        }
        
        return new TextAndMoreTextValue(text, moreText);
    }
    
    private static String buildText(Context context, FilterOption option, List lists) {
        
        String firstItem = buildItemIndex(context, option, lists, 0);
        
        String secondItem = buildItemIndex(context, option, lists, 1);
        
        return TextUtils.build(firstItem, ", ", secondItem).trim();
    }
    
    private static String buildMoreText(Context context, FilterOption option, List lists) {
        int size = lists.size();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(", ");
        for (int i = 2; i < size; i++) {
            String item = buildItemIndex(context, option, lists, i);
            stringBuilder
                      .append(item)
                      .append(", ");
        }
        
        return stringBuilder.toString().trim();
    }
    
    private static String buildItemIndex(Context context, FilterOption option, List lists,
              int index) {
        try {
            String item = "";
            if (option == FilterOption.PROPERTY_TYPE) {
                item = context
                          .getString(((PropertyType) lists.get(index)).getDisplayStringResource());
            } else if (option == FilterOption.TAGS) {
                item = ((Tag) lists.get(index)).getName();
            }
            
            return item.trim();
        } catch (IndexOutOfBoundsException ex) {
            return "";
        }
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getMoreText() {
        return moreText;
    }
    
    public void setMoreText(String moreText) {
        this.moreText = moreText;
    }
    
    @Override
    public String toString() {
        return "TextAndMoreTextValue{" +
               "text='" + text + '\'' +
               ", moreText='" + moreText + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeString(text);
        parcel.writeString(moreText);
    }
}
