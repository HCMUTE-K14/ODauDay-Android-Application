package com.odauday.ui.search.common;

import android.content.Context;
import com.odauday.R;
import com.odauday.model.Tag;
import com.odauday.ui.search.navigation.FilterOption;
import com.odauday.ui.search.navigation.PropertyType;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 4/7/18.
 */

public class TextAndMoreTextValue {
    
    private String text;
    private String moreText;
    
    public TextAndMoreTextValue(String text, String moreText) {
        this.text = text;
        this.moreText = moreText;
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
    
    @SuppressWarnings("unchecked")
    public static TextAndMoreTextValue build(Context context, FilterOption option,
              List lists) {
        String text = "";
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
        
        return TextUtils.build(firstItem, ", ", secondItem);
    }
    
    private static String buildMoreText(Context context, FilterOption option, List lists) {
        int size = lists.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < size; i++) {
            if (i == size - 1) {
                String lastItem = buildItemIndex(context, option, lists, i);
                
                stringBuilder.append(lastItem);
                break;
            }
            String item = buildItemIndex(context, option, lists, i);
            stringBuilder
                      .append(item)
                      .append(", ");
        }
        return stringBuilder.toString();
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
    
}
