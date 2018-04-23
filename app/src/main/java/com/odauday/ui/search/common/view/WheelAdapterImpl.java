package com.odauday.ui.search.common.view;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.ui.view.wheelview.adapters.WheelViewAdapter;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 4/2/18.
 */

public class WheelAdapterImpl implements WheelViewAdapter {
    
    private final FilterNumberPickerDialog mPickerDialog;
    private final LayoutInflater mLayoutInflater;
    private int[] mDisplayIntegers;
    private String[] mDisplayString;
    private final boolean mIsMinAdapter;
    private final boolean mIsUsingMinMax;
    
    public WheelAdapterImpl(FilterNumberPickerDialog pickerDialog, String[] displayStrings,
              boolean isMinAdapter, boolean isUsingMinMax) {
        this.mPickerDialog = pickerDialog;
        this.mDisplayString = displayStrings;
        this.mIsMinAdapter = isMinAdapter;
        this.mIsUsingMinMax = isUsingMinMax;
        this.mLayoutInflater = LayoutInflater.from(mPickerDialog.getActivity());
    }
    
    public WheelAdapterImpl(FilterNumberPickerDialog pickerDialog, int[] displayIntegers,
              boolean isMinAdapter, boolean isUsingMinMax) {
        this.mPickerDialog = pickerDialog;
        this.mDisplayIntegers = displayIntegers;
        this.mIsMinAdapter = isMinAdapter;
        this.mIsUsingMinMax = isUsingMinMax;
        this.mLayoutInflater = LayoutInflater.from(mPickerDialog.getActivity());
    }
    
    public int getItemsCount() {
        return mPickerDialog.mIsCurrency ? this.mDisplayIntegers.length + 1
                  : this.mDisplayString.length;
    }
    
    public View getItem(int index, View convertView, ViewGroup parent) {
        TextView txb;
        if (convertView == null) {
            txb = (TextView) this.mLayoutInflater
                      .inflate(R.layout.item_filter_picker, parent, false);
        } else {
            txb = (TextView) convertView;
        }
        if (mPickerDialog.mIsCurrency && index == 0) {
            txb.setText(R.string.txt_any);
        } else if (mPickerDialog.mIsCurrency) {
            if (this.mIsUsingMinMax) {
                index--;
            }
            txb.setText(TextUtils.formatIntWithoutCurrency((float) this.mDisplayIntegers[index],
                      false));
        } else {
            txb.setText(this.mDisplayString[index]);
        }
        return txb;
    }
    
    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }
    
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
}
