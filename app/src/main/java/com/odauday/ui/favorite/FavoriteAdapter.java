package com.odauday.ui.favorite;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemPropertyBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class FavoriteAdapter extends BaseAdapter<Property,ItemPropertyBinding> {
    public FavoriteAdapter() {
    
    }
    
    @Override
    protected ItemPropertyBinding createBinding(ViewGroup parent) {
        ItemPropertyBinding itemPropertyBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.item_property,parent,false);
        return itemPropertyBinding;
    }
    
    @Override
    protected void bind(ItemPropertyBinding binding, Property item) {
        binding.setProperty(item);
        binding.setHandler(this);
        //ImageLoader.load(binding.imageRoom,item.getImages().get(0).getUrl().toString().trim());
    }
    
    @Override
    protected boolean areItemsTheSame(Property oldItem, Property newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Property oldItem, Property newItem) {
        return oldItem.equals(newItem);
    }
    
    @Override
    public void setData(List<Property> _data) {
        if(data==null){
            data=new ArrayList<>();
        }
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    public void onClickProperty(Property property){
    
    }
}
