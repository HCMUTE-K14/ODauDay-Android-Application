package com.odauday.ui.search.autocomplete;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.databinding.RowItemSearchBinding;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceAdapter.OnClickItemSearchPlace;
import com.odauday.utils.ImageLoader;

/**
 * Created by infamouSs on 4/23/18.
 */
public class RowItemAdapter extends BaseAdapter<AutoCompletePlace, RowItemSearchBinding> {
    
    
    private final OnClickItemSearchPlace mOnClickItemSearchPlace;
    
    private final boolean mIsNeedShowRemoveButton;
    
    public RowItemAdapter(OnClickItemSearchPlace onClickItemSearchPlace,
        boolean isNeedShowRemoveButton) {
        this.mOnClickItemSearchPlace = onClickItemSearchPlace;
        this.mIsNeedShowRemoveButton = isNeedShowRemoveButton;
    }
    
    @Override
    protected RowItemSearchBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.row_item_search,
                parent, false, null);
    }
    
    @Override
    protected void bind(RowItemSearchBinding binding, AutoCompletePlace item) {
        binding.setItem(item);
        if (mIsNeedShowRemoveButton) {
            binding.imageRemove.setVisibility(View.VISIBLE);
            binding.imageRemove.setOnClickListener(view -> {
                if (mOnClickItemSearchPlace != null) {
                    mOnClickItemSearchPlace.onRemoveRecentSearchPlace(item);
                }
            });
        }
        if (item.isSearched()) {
            ImageLoader.load(binding.imageIcon, R.drawable.ic_place_history);
        }
        binding.getRoot().setOnClickListener(view -> {
            if (mOnClickItemSearchPlace != null) {
                mOnClickItemSearchPlace.onSelectedSuggestionPlace(item);
            }
        });
    }
    
    @Override
    protected boolean areItemsTheSame(AutoCompletePlace oldItem, AutoCompletePlace newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(AutoCompletePlace oldItem, AutoCompletePlace newItem) {
        return oldItem.equals(newItem);
    }
}
