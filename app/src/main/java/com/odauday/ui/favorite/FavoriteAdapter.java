package com.odauday.ui.favorite;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemPropertyBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.view.StarView;
import com.odauday.ui.view.StarView.OnClickStarListener;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class FavoriteAdapter extends BaseAdapter<Property,ItemPropertyBinding> {
    public static final String TAG=FavoriteAdapter.class.getSimpleName();
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
        binding.starView.setOnClickStarListener(mOnClickStarListener);
        
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
    StarView.OnClickStarListener mOnClickStarListener=new OnClickStarListener() {
        @Override
        public void onCheckStar(Property property) {
            Timber.tag(TAG).d("Check: "+property.getName());
        }
        @Override
        public void onUnCheckStar(Property property) {
            Timber.tag(TAG).d("UnCheck: "+property.getName());
        }
    };
    public void onClickFavorite(View view, Property property){
        ((StarView)view).addOnClick(property);
    }
}
