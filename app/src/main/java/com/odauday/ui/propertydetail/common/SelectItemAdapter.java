package com.odauday.ui.propertydetail.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemSelectItemBinding;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.utils.ImageLoader;

/**
 * Created by infamouSs on 5/17/18.
 */
public class SelectItemAdapter<T extends ItemSelectModel> extends
                                                          BaseAdapter<T, ItemSelectItemBinding> {
    
    private OnClickItemListener<T> mOnClickItemListener;
    
    public SelectItemAdapter(OnClickItemListener<T> listener) {
        this.mOnClickItemListener = listener;
    }
    
    @Override
    protected ItemSelectItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_select_item, parent, false);
    }
    
    @Override
    protected void bind(ItemSelectItemBinding binding, T item) {
        binding.container.setOnClickListener(view -> {
            if (mOnClickItemListener != null) {
                mOnClickItemListener.onClickItem(item);
            }
        });
        if (item.getType() == ItemSelectModel.EMAIL) {
            ImageLoader.loadWithoutOptions(binding.image, R.drawable.ic_mail);
        } else if (item.getType() == ItemSelectModel.PHONE) {
            ImageLoader.load(binding.image, R.drawable.ic_call);
        }
        binding.text.setText(item.getText());
    }
    
    @Override
    protected boolean areItemsTheSame(T oldItem, T newItem) {
        return false;
    }
    
    @Override
    protected boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }
    
    public interface OnClickItemListener<T> {
        
        void onClickItem(T value);
    }
}
