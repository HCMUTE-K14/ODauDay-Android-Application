package com.odauday.ui.search.common.view.propertydialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemTextWithCheckBoxBinding;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.search.navigation.PropertyType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class PropertyTypeDialogAdapter extends
                                       BaseAdapter<PropertyDialogItem, ItemTextWithCheckBoxBinding> {
    
    private List<Integer> mSelectedPropertyType;
    
    public PropertyTypeDialogAdapter(Context context, List<Integer> selectedPropertyType) {
        this.setData(buildDataForAdapter(context, selectedPropertyType));
    }
    
    
    private List<PropertyDialogItem> buildDataForAdapter(Context context,
        List<Integer> selectedPropertyType) {
        List<PropertyDialogItem> list = new ArrayList<>();
        if (selectedPropertyType == null) {
            selectedPropertyType = new ArrayList<>();
        }
        mSelectedPropertyType = selectedPropertyType;
        for (PropertyType type : PropertyType.values()) {
            String displayName = context.getString(type.getDisplayStringResource());
            boolean isSelected = selectedPropertyType.contains(type.getId());
            list.add(new PropertyDialogItem(type.getId(), displayName, isSelected));
        }
        
        return list;
    }
    
    @Override
    protected ItemTextWithCheckBoxBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_text_with_check_box,
                parent, false, null);
    }
    
    @Override
    protected void bind(ItemTextWithCheckBoxBinding binding, PropertyDialogItem item) {
        binding.setItem(item);
        binding.getRoot().setOnClickListener(view -> {
            if (binding.checkBox.isChecked()) {
                mSelectedPropertyType.remove(Integer.valueOf(binding.getItem().getId()));
                binding.checkBox.setChecked(false);
                return;
            }
            mSelectedPropertyType.add(binding.getItem().getId());
            binding.checkBox.setChecked(true);
        });
    }
    
    @Override
    protected boolean areItemsTheSame(PropertyDialogItem oldItem, PropertyDialogItem newItem) {
        return oldItem.getDisplayString().equals(newItem.getDisplayString());
    }
    
    @Override
    protected boolean areContentsTheSame(PropertyDialogItem oldItem, PropertyDialogItem newItem) {
        return oldItem == newItem;
    }
    
    public List<Integer> getSelectedPropertyType() {
        return mSelectedPropertyType;
    }
    
    public void setSelectedPropertyType(List<Integer> selectedPropertyType) {
        mSelectedPropertyType = selectedPropertyType;
    }
}
