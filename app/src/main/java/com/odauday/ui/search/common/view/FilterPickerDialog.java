package com.odauday.ui.search.common.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.view.wheelview.OnWheelScrollListener;
import com.odauday.ui.view.wheelview.WheelView;

/**
 * Created by infamouSs on 4/2/18.
 */

public class FilterPickerDialog extends BaseDialogFragment implements OnWheelScrollListener {
    
    public boolean mHasToWheel;
    public boolean mIsCurrency;
    public FilterPicker mFilterPickerFrom;
    public FilterPicker mFilterPickerTo;
    
    public static FilterPickerDialog newInstance(Builder builder) {
        
        if (!builder.isCurrency) {
            return FilterPickerDialog
                      .newInstance(builder.title, builder.valueRes, builder.displayValueRes,
                                builder.selectedFrom, builder.selectedTo, builder.hasToWheel);
        } else {
            return FilterPickerDialog.newInstanceWithCurrency(builder.title, builder.valueRes,
                      builder.selectedFrom, builder.selectedTo);
        }
    }
    
    private static FilterPickerDialog newInstance(
              String title, int valueRes,
              int displayValueRes, int selectedFrom,
              int selectedTo, boolean hasToWheel) {
        
        FilterPickerDialog dialog = new FilterPickerDialog();
        
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putInt(Constants.INTENT_EXTRA_VALUE_RES, valueRes);
        bundle.putInt(Constants.INTENT_EXTRA_VALUE_STRING_RES, displayValueRes);
        bundle.putInt(Constants.INTENT_EXTRA_SELECTION_FROM, selectedFrom);
        bundle.putInt(Constants.INTENT_EXTRA_SELECTION_TO, selectedTo);
        bundle.putBoolean(Constants.INTENT_EXTRA_HAS_TO_WHEEL, hasToWheel);
        bundle.putBoolean(Constants.INTENT_EXTRA_IS_CURRENCY, false);
        dialog.setArguments(bundle);
        
        return dialog;
    }
    
    private static FilterPickerDialog newInstanceWithCurrency(
              String title, int valueRes,
              int selectedFrom, int selectedTo) {
        FilterPickerDialog dialog = new FilterPickerDialog();
        
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putInt(Constants.INTENT_EXTRA_VALUE_RES, valueRes);
        bundle.putInt(Constants.INTENT_EXTRA_SELECTION_FROM, selectedFrom);
        bundle.putInt(Constants.INTENT_EXTRA_SELECTION_TO, selectedTo);
        bundle.putBoolean(Constants.INTENT_EXTRA_HAS_TO_WHEEL, true);
        bundle.putBoolean(Constants.INTENT_EXTRA_IS_CURRENCY, true);
        dialog.setArguments(bundle);
        
        return dialog;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need bundle to init this dialog");
        }
        
        this.mIsCurrency = getArguments().getBoolean(Constants.INTENT_EXTRA_IS_CURRENCY, false);
        this.mHasToWheel = getArguments().getBoolean(Constants.INTENT_EXTRA_HAS_TO_WHEEL, true);
        
        View v = View.inflate(getActivity(), R.layout.layout_dialog_choose_max_min, null);
        this.mFilterPickerFrom = v.findViewById(R.id.filter_from);
        this.mFilterPickerTo = v.findViewById(R.id.filter_to);
        this.mFilterPickerFrom.addScrollingListener(this);
        this.mFilterPickerTo.addScrollingListener(this);
        
        if (this.mIsCurrency) {
            int[] intValues = getResources()
                      .getIntArray(getArguments().getInt(Constants.INTENT_EXTRA_VALUE_RES));
            this.mFilterPickerFrom
                      .setViewAdapter(new WheelAdapterImpl(this, intValues, true, true));
            this.mFilterPickerTo
                      .setViewAdapter(new WheelAdapterImpl(this, intValues, false, true));
        } else {
            String[] displayedValues = getResources().getStringArray(
                      getArguments().getInt(Constants.INTENT_EXTRA_VALUE_STRING_RES));
            this.mFilterPickerFrom
                      .setViewAdapter(new WheelAdapterImpl(this, displayedValues, true, true));
            this.mFilterPickerTo
                      .setViewAdapter(new WheelAdapterImpl(this, displayedValues, false, true));
        }
        int currentItemFrom = getSelectedValue(
                  getArguments().getInt(Constants.INTENT_EXTRA_SELECTION_FROM));
        this.mFilterPickerFrom.setCurrentItem(currentItemFrom);
        this.mFilterPickerFrom.setHeading(getString(R.string.txt_min));
        
        if (this.mHasToWheel) {
            int currentItemTo = getSelectedValue(
                      getArguments().getInt(Constants.INTENT_EXTRA_SELECTION_TO));
            this.mFilterPickerTo.setCurrentItem(currentItemTo);
            this.mFilterPickerTo.setHeading(getString(R.string.txt_max));
        } else {
            this.mFilterPickerTo.setVisibility(View.GONE);
        }
        
        setTitle(getArguments().getString("TITLE"));
        setContent(v);
        setPositiveButton(getString(R.string.txt_done), null);
        final AlertDialog dialog = (AlertDialog) create();
        setOnShowDialog(dialogInterface -> {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                      .setOnClickListener(new OnShowFilterPickerDialogEvent());
        });
        return dialog;
    }
    
    private int getSelectedValue(int selectedValue) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need bundle to init this dialog");
        }
        int[] intValues = getResources()
                  .getIntArray(getArguments().getInt(Constants.INTENT_EXTRA_VALUE_RES));
        if (this.mIsCurrency && selectedValue == 0) {
            return 0;
        }
        int i = 0;
        while (i < intValues.length) {
            if (intValues[i] != selectedValue) {
                i++;
            } else if (this.mIsCurrency) {
                return i + 1;
            } else {
                return i;
            }
        }
        return 0;
    }
    
    @Override
    public void onScrollingStarted(WheelView wheel) {
    
    }
    
    @Override
    public void onScrollingFinished(WheelView wheel) {
        switch (wheel.getId()) {
            case R.id.filter_from:
                if (this.mFilterPickerTo.getCurrentItem() > 0 &&
                    this.mFilterPickerFrom.getCurrentItem() >
                    this.mFilterPickerTo.getCurrentItem()) {
                    this.mFilterPickerTo
                              .setCurrentItem(this.mFilterPickerFrom.getCurrentItem(), true);
                    return;
                }
                return;
            case R.id.filter_to:
                if (this.mFilterPickerTo.getCurrentItem() != 0 &&
                    this.mFilterPickerTo.getCurrentItem() <
                    this.mFilterPickerFrom.getCurrentItem()) {
                    this.mFilterPickerFrom
                              .setCurrentItem(this.mFilterPickerTo.getCurrentItem(), true);
                    return;
                }
                return;
            default:
                return;
        }
    }
    
    private int getIntValue(FilterPicker picker) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need bundle to init this dialog");
        }
        int index = picker.getCurrentItem();
        int[] intValues = getResources()
                  .getIntArray(getArguments().getInt(Constants.INTENT_EXTRA_VALUE_RES));
        if (!this.mIsCurrency) {
            return intValues[index];
        }
        if (index == 0) {
            return index;
        }
        return intValues[index - 1];
    }
    
    public interface OnCompletePickerListener {
        
        void onCompletePicker(int requestCode, int pickedValueFrom, int pickedValueTo);
    }
    
    public static class Builder {
        
        String title;
        int valueRes;
        int displayValueRes;
        int selectedFrom;
        int selectedTo;
        boolean hasToWheel;
        boolean isCurrency;
        
        public Builder(boolean isCurrency) {
            this.isCurrency = isCurrency;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setValueRes(int valueRes) {
            this.valueRes = valueRes;
            return this;
        }
        
        public Builder setDisplayValueRes(int displayValueRes) {
            this.displayValueRes = displayValueRes;
            return this;
        }
        
        public Builder setSelectedFrom(int selectedFrom) {
            this.selectedFrom = selectedFrom;
            return this;
        }
        
        public Builder setSelectedTo(int selectedTo) {
            this.selectedTo = selectedTo;
            return this;
        }
        
        public Builder setHasToWheel(boolean hasToWheel) {
            this.hasToWheel = hasToWheel;
            return this;
        }
        
        public Builder setCurrency(boolean currency) {
            isCurrency = currency;
            return this;
        }
        
        public FilterPickerDialog build() {
            return FilterPickerDialog.newInstance(this);
        }
    }
    
    class OnShowFilterPickerDialogEvent implements View.OnClickListener {
        
        @Override
        public void onClick(View view) {
            Fragment fragment = FilterPickerDialog.this.getTargetFragment();
            if (fragment != null && (fragment instanceof OnCompletePickerListener)) {
                int toValue;
                if (FilterPickerDialog.this.mHasToWheel) {
                    toValue = FilterPickerDialog.this
                              .getIntValue(FilterPickerDialog.this.mFilterPickerTo);
                } else {
                    toValue = 0;
                }
                int fromValue = FilterPickerDialog.this
                          .getIntValue(FilterPickerDialog.this.mFilterPickerFrom);
                if (!FilterPickerDialog.this.mHasToWheel || toValue <= 0 || toValue >= fromValue) {
                    ((OnCompletePickerListener) fragment)
                              .onCompletePicker(FilterPickerDialog.this.getTargetRequestCode(),
                                        fromValue,
                                        toValue);
                    FilterPickerDialog.this.dismiss();
                    return;
                }
                Toast.makeText(FilterPickerDialog.this.getActivity(),
                          R.string.message_max_value_less_than_min_value, Toast.LENGTH_SHORT)
                          .show();
            }
        }
    }
}
