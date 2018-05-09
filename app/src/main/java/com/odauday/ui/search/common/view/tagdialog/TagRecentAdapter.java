package com.odauday.ui.search.common.view.tagdialog;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemTextViewOnlyBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseAdapter;
import java.util.List;

/**
 * Created by infamouSs on 4/5/2018.
 */
public class TagRecentAdapter extends BaseAdapter<Tag, ItemTextViewOnlyBinding> {
    
    private OnClickTagRecent mOnClickTagRecent;
    
    public TagRecentAdapter(List<Tag> recentTags, OnClickTagRecent onClickTagRecent) {
        setData(recentTags);
        setOnClickTagRecent(onClickTagRecent);
    }
    
    public TagRecentAdapter(List<Tag> recentTags) {
        setData(recentTags);
    }
    
    public void setOnClickTagRecent(
        OnClickTagRecent onClickTagRecent) {
        mOnClickTagRecent = onClickTagRecent;
    }
    
    @Override
    protected ItemTextViewOnlyBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_view_only,
                parent, false, null);
    }
    
    @Override
    protected void bind(ItemTextViewOnlyBinding binding, Tag item) {
        binding.text.setText(item.getName());
        binding.getRoot().setOnClickListener(view -> {
            if (mOnClickTagRecent != null) {
                mOnClickTagRecent.onRecentTagClick(item);
            }
        });
    }
    
    @Override
    protected boolean areItemsTheSame(Tag oldItem, Tag newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Tag oldItem, Tag newItem) {
        return oldItem == newItem;
    }
    
    public interface OnClickTagRecent {
        
        void onRecentTagClick(Tag tag);
    }
}
