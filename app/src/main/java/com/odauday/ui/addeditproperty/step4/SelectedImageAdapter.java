package com.odauday.ui.addeditproperty.step4;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemSelectedImageBinding;
import com.odauday.model.Image;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.utils.ImageLoader;

/**
 * Created by infamouSs on 4/29/18.
 */
public class SelectedImageAdapter extends BaseAdapter<Image, ItemSelectedImageBinding> {
    
    
    private RemoveImageSelectedListener mRemoveImageSelectedListener;
    
    public SelectedImageAdapter(RemoveImageSelectedListener removeImageSelectedListener) {
        this.mRemoveImageSelectedListener = removeImageSelectedListener;
    }
    
    public void remove(Image imageGallery, int position) {
        data.remove(imageGallery);
        notifyDataSetChanged();
    }
    
    @Override
    protected ItemSelectedImageBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_selected_image,
                parent, false, null);
    }
    
    @Override
    protected void bind(ItemSelectedImageBinding binding, Image item) {
        binding.checkTextView.bringToFront();
        binding.checkTextView.setChecked(true);
        ImageLoader.load(binding.imageViewFile, item.getUrl());
        binding.imageViewFile.setOnClickListener(view -> {
            mRemoveImageSelectedListener.onRemoveImage(item, data.indexOf(item));
        });
    }
    
    @Override
    protected boolean areItemsTheSame(Image oldItem, Image newItem) {
        return false;
    }
    
    @Override
    protected boolean areContentsTheSame(Image oldItem, Image newItem) {
        return false;
    }
    
    public interface RemoveImageSelectedListener {
        
        void onRemoveImage(Image image, int position);
    }
}
