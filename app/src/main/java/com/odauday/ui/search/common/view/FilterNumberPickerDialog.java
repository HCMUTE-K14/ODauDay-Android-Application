package com.odauday.ui.search.common.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.MinMaxObject;
import com.odauday.ui.view.wheelview.OnWheelScrollListener;
import com.odauday.ui.view.wheelview.WheelView;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/2/18.
 */

public class FilterNumberPickerDialog extends BaseDialogFragment implements OnWheelScrollListener {
    
    public boolean mHasToWheel;
    public boolean mIsCurrency;

    public FilterNumberPicker mFilterNumberPickerFrom;
    public FilterNumberPicker mFilterNumberPickerTo;
    
    public static FilterNumberPickerDialog newInstance(Builder builder) {
        
        if (!builder.isCurrency) {
            return FilterNumberPickerDialog
                      .newInstance(builder.title, builder.valueRes, builder.displayValueRes,
                                builder.selectedFrom, builder.selectedTo, builder.hasToWheel);
        } else {
            return FilterNumberPickerDialog.newInstanceWithCurrency(builder.title, builder.valueRes,
                      builder.selectedFrom, builder.selectedTo);
        }
    }
    
    private static FilterNumberPickerDialog newInstance(
              String title, int valueRes,
              int displayValueRes, int selectedFrom,
              int selectedTo, boolean hasToWheel) {
        
        FilterNumberPickerDialog dialog = new FilterNumberPickerDialog();
        
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
    
    private static FilterNumberPickerDialog newInstanceWithCurrency(
              String title, int valueRes,
              int selectedFrom, int selectedTo) {
        FilterNumberPickerDialog dialog = new FilterNumberPickerDialog();
        
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
        this.mFilterNumberPickerFrom = v.findViewById(R.id.filter_from);
        this.mFilterNumberPickerTo = v.findViewById(R.id.filter_to);
        this.mFilterNumberPickerFrom.addScrollingListener(this);
        this.mFilterNumberPickerTo.addScrollingListener(this);
        
        if (this.mIsCurrency) {
            int[] intValues = getResources()
                      .getIntArray(getArguments().getInt(Constants.INTENT_EXTRA_VALUE_RES));
            this.mFilterNumberPickerFrom
                      .setViewAdapter(new WheelAdapterImpl(this, intValues, true, true));
            this.mFilterNumberPickerTo
                      .setViewAdapter(new WheelAdapterImpl(this, intValues, false, true));
        } else {
            String[] displayedValues = getResources().getStringArray(
                      getArguments().getInt(Constants.INTENT_EXTRA_VALUE_STRING_RES));
            this.mFilterNumberPickerFrom
                      .setViewAdapter(new WheelAdapterImpl(this, displayedValues, true, true));
            this.mFilterNumberPickerTo
                      .setViewAdapter(new WheelAdapterImpl(this, displayedValues, false, true));
        }
        int currentItemFrom = getSelectedValue(
                  getArguments().getInt(Constants.INTENT_EXTRA_SELECTION_FROM));
        this.mFilterNumberPickerFrom.setCurrentItem(currentItemFrom);
        this.mFilterNumberPickerFrom.setHeading(getString(R.string.txt_min));
        
        if (this.mHasToWheel) {
            int currentItemTo = getSelectedValue(
                      getArguments().getInt(Constants.INTENT_EXTRA_SELECTION_TO));
            this.mFilterNumberPickerTo.setCurrentItem(currentItemTo);
            this.mFilterNumberPickerTo.setHeading(getString(R.string.txt_max));
        } else {
            this.mFilterNumberPickerTo.setVisibility(View.GONE);
        }
        
        setTitle(getArguments().getString("TITLE"));
        setContent(v);
        setPositiveButton(getString(R.string.txt_done), false,
                  new OnShowFilterPickerDialogEvent());
        return create();
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
    
    public void onPause() {
        super.onPause();
        this.mFilterNumberPickerFrom.removeScrollingListener(this);
        this.mFilterNumberPickerTo.removeScrollingListener(this);
    }
    
    
    @Override
    public void onScrollingStarted(WheelView wheel) {

    }
    
    @Override
    public void onScrollingFinished(WheelView wheel) {
        switch (wheel.getId()) {
            case R.id.filter_from:
                if (this.mFilterNumberPickerTo.getCurrentItem() > 0 &&
                    this.mFilterNumberPickerFrom.getCurrentItem() >
                    this.mFilterNumberPickerTo.getCurrentItem()) {
                    this.mFilterNumberPickerTo
                              .setCurrentItem(this.mFilterNumberPickerFrom.getCurrentItem(), true);
                    return;
                }
                return;
            case R.id.filter_to:
                if (this.mFilterNumberPickerTo.getCurrentItem() != 0 &&
                    this.mFilterNumberPickerTo.getCurrentItem() <
                    this.mFilterNumberPickerFrom.getCurrentItem()) {
                    this.mFilterNumberPickerFrom
                              .setCurrentItem(this.mFilterNumberPickerTo.getCurrentItem(), true);
                    return;
                }
                return;
            default:
                break;
        }
    }
    
    private int getIntValue(FilterNumberPicker picker) {
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
    
    private String getStringValue(FilterNumberPicker picker) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need bundle to init this dialog");
        }
        
        if (getArguments().getInt(Constants.INTENT_EXTRA_VALUE_STRING_RES) == 0x00) {
            return "";
        }
        
        int index = picker.getCurrentItem();
        String[] stringValues = getResources()
                  .getStringArray(getArguments().getInt(Constants.INTENT_EXTRA_VALUE_STRING_RES));
        
        if (!this.mIsCurrency) {
            return stringValues[index];
        }
        if (index == 0) {
            return stringValues[index];
        }
        return stringValues[index - 1];
    }
    
    public interface OnCompletePickedNumberListener {
        
        void onCompletePickedNumber(int requestCode, PickerMinMaxReturnObject object);
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
        
        public FilterNumberPickerDialog build() {
            return FilterNumberPickerDialog.newInstance(this);
        }
    }
    
    class OnShowFilterPickerDialogEvent implements View.OnClickListener {
        
        @Override
        public void onClick(View view) {
            Fragment fragment = FilterNumberPickerDialog.this.getTargetFragment();
            if (fragment != null && (fragment instanceof OnCompletePickedNumberListener)) {
                int toValue;
                if (FilterNumberPickerDialog.this.mHasToWheel) {
                    toValue = FilterNumberPickerDialog.this
                              .getIntValue(FilterNumberPickerDialog.this.mFilterNumberPickerTo);
                } else {
                    toValue = 0;
                }
                int fromValue = FilterNumberPickerDialog.this
                          .getIntValue(FilterNumberPickerDialog.this.mFilterNumberPickerFrom);
                if (!FilterNumberPickerDialog.this.mHasToWheel || toValue <= 0 ||
                    toValue >= fromValue) {
                    MinMaxObject<String> display = new MinMaxObject<>(
                              getStringValue(mFilterNumberPickerFrom),
                              getStringValue(mFilterNumberPickerTo));
                    
                    MinMaxObject<Integer> value = new MinMaxObject<>(fromValue, toValue);
                    
                    PickerMinMaxReturnObject returnObject = new PickerMinMaxReturnObject(display,
                              value);
                    ((OnCompletePickedNumberListener) fragment)
                              .onCompletePickedNumber(
                                        FilterNumberPickerDialog.this.getTargetRequestCode(),
                                        returnObject);
                    FilterNumberPickerDialog.this.dismiss();
                    
                    Timber.tag("PICKER")
                              .d("STRING_FROM:" + getStringValue(mFilterNumberPickerFrom));
                    Timber.tag("PICKER").d("STRING_TO:" + getStringValue(mFilterNumberPickerTo));
                    
                    return;
                }
                Toast.makeText(FilterNumberPickerDialog.this.getActivity(),
                          R.string.message_max_value_less_than_min_value, Toast.LENGTH_SHORT)
                          .show();
            }
        }
    }
}
