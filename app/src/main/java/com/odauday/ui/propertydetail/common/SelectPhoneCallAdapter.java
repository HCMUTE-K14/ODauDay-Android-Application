package com.odauday.ui.propertydetail.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemSelectPhoneNumberBinding;
import com.odauday.model.MyPhone;
import com.odauday.ui.base.BaseAdapter;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/16/18.
 */
public class SelectPhoneCallAdapter extends BaseAdapter<MyPhone, ItemSelectPhoneNumberBinding> {
    
    private OnSelectPhoneNumberListener mOnSelectPhoneNumberListener;
    
    public SelectPhoneCallAdapter(OnSelectPhoneNumberListener onSelectPhoneNumberListener) {
        this.mOnSelectPhoneNumberListener = onSelectPhoneNumberListener;
    }
    
    @Override
    protected ItemSelectPhoneNumberBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_select_phone_number, parent, false);
    }
    
    @Override
    protected void bind(ItemSelectPhoneNumberBinding binding, MyPhone item) {
        binding.container.setOnClickListener(view -> {
            if (mOnSelectPhoneNumberListener != null) {
                mOnSelectPhoneNumberListener.onSelectPhoneNumber(item.getPhoneNumber());
            }
        });
        
        binding.phoneNumber.setText(item.getPhoneNumber());
    }
    
    @Override
    protected boolean areItemsTheSame(MyPhone oldItem, MyPhone newItem) {
        return false;
    }
    
    @Override
    protected boolean areContentsTheSame(MyPhone oldItem, MyPhone newItem) {
        return false;
    }
    
    public void setOnSelectPhoneNumberListener(
        OnSelectPhoneNumberListener onSelectPhoneNumberListener) {
        mOnSelectPhoneNumberListener = onSelectPhoneNumberListener;
    }
    
    public interface OnSelectPhoneNumberListener {
        
        void onSelectPhoneNumber(String phoneNumber);
    }
}
