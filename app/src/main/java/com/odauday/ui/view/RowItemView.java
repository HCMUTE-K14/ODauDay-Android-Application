package com.odauday.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.model.MyEmail;
import com.odauday.model.MyPhone;
import com.odauday.ui.addeditproperty.step1.PhoneAndEmailEnum;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/25/18.
 */
public class RowItemView extends LinearLayout {
    
    
    private int mTag = 0;
    private int mText = 0;
    private int mImageUrl = 0;
    private int mTypeInput = 0;
    private LinearLayout mRowItemContainer;
    private RowAddedCallBack mRowAddedCallBack;
    
    public RowItemView(Context context) {
        super(context);
        init(context);
    }
    
    public RowItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public RowItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    public String getTextValue() {
        int sizeView = mRowItemContainer.getChildCount();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sizeView; i++) {
            AddRowItemView addRowItemView = (AddRowItemView) mRowItemContainer.getChildAt(i);
            if (addRowItemView != null) {
                String value = addRowItemView.getTextView().getText().toString();
                builder.append(value);
            }
        }
        return builder.toString();
    }
    
    public List getRawValue(int type) {
        int sizeView = mRowItemContainer.getChildCount();
        
        if (type == PhoneAndEmailEnum.EMAIL.getId()) {
            List<MyEmail> emails = new ArrayList<>();
            for (int i = 0; i < sizeView; i++) {
                AddRowItemView addRowItemView = (AddRowItemView) mRowItemContainer.getChildAt(i);
                if (addRowItemView != null) {
                    String value = addRowItemView.getTextView().getText().toString();
                    emails.add(new MyEmail("", value));
                }
            }
            return emails;
        } else if (type == PhoneAndEmailEnum.PHONE.getId()) {
            List<MyPhone> phones = new ArrayList<>();
            for (int i = 0; i < sizeView; i++) {
                AddRowItemView addRowItemView = (AddRowItemView) mRowItemContainer.getChildAt(i);
                if (addRowItemView != null) {
                    String value = addRowItemView.getTextView().getText().toString();
                    phones.add(new MyPhone("", value));
                }
            }
            return phones;
        } else {
            return null;
        }
    }
    
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_row_view, this, true);
        mRowItemContainer = rootView.findViewById(R.id.row_item_container);
    }
    
    public void addRow(Context context, int typeInput, int text, int imageUrl,
              RowAddedCallBack rowAddedCallBack) {
        mText = text;
        mImageUrl = imageUrl;
        mTypeInput = typeInput;
        mRowAddedCallBack = rowAddedCallBack;
        
        AddRowItemView row = new AddRowItemView(context);
        
        row.getTextView().setHint(text);
        row.setTypeText(typeInput);
        
        row.setImage(mImageUrl);
        row.setPlusOrMinusButtonTag(10);
        row.getPlusOrMinusBtn()
                  .setOnClickListener(new AddClickListener(row.getPlusOrMinusBtn(), row));
        
        mRowItemContainer.addView(row);
    }
    
    public interface RowAddedCallBack {
        
        void rowItemAddedCallBack();
    }
    
    class AddClickListener implements OnClickListener {
        
        private ImageView mImageView;
        private View mLayout;
        
        public AddClickListener(ImageView imageView, View layout) {
            this.mImageView = imageView;
            this.mLayout = layout;
        }
        
        @Override
        public void onClick(View v) {
            if ((Integer) mImageView.getTag() == 10) {
                addRow(getContext(), mTypeInput, mText, mImageUrl, mRowAddedCallBack);
                mImageView.setImageDrawable(
                          ContextCompat.getDrawable(getContext(), R.drawable.ic_minus_gray));
                mImageView.setTag(100);
                mRowAddedCallBack.rowItemAddedCallBack();
                return;
            }
            mRowItemContainer.removeView(mLayout);
            ImageView imageLastItem = (ImageView) mRowItemContainer
                      .getChildAt(mRowItemContainer.getChildCount() - 1)
                      .findViewById(R.id.plus_or_minus_btn);
            imageLastItem.setImageDrawable(
                      ContextCompat.getDrawable(getContext(), R.drawable.ic_plus_blue));
            imageLastItem.setTag(10);
        }
    }
}
