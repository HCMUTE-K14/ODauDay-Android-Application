package com.odauday.ui.user.tag;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.TagItemBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseAdapter;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/3/2018.
 */

public class TagAdapter extends BaseAdapter<Tag, TagItemBinding> {
    
    @Override
    protected TagItemBinding createBinding(ViewGroup parent) {
        TagItemBinding itemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.tag_item, parent, false);
        return itemBinding;
    }
    
    @Override
    protected void bind(TagItemBinding binding, Tag item) {
        binding.setTag(item);
        binding.setHandler(this);
        
    }
    
    @Override
    protected boolean areItemsTheSame(Tag oldItem, Tag newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Tag oldItem, Tag newItem) {
        return oldItem.equals(newItem);
    }
    
    public void onClickTag(Tag tag) {
        Log.d("Lng", "sdkfjdsf");
        Timber.tag("Song song ki").d(tag.getName());
    }
}
