package com.odauday.ui.favorite;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.api.EndPoint;
import com.odauday.databinding.ItemPropertyBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.view.StarView;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class FavoriteAdapter extends BaseAdapter<Property, ItemPropertyBinding> {
    
    public static final String TAG = FavoriteAdapter.class.getSimpleName();
    private OnClickStarListener mOnClickStarListeners;
    StarView.OnClickStarListener<Property> mOnClickStarListener = new StarView.OnClickStarListener<Property>() {
        @Override
        public void onCheckStar(Property item) {
            
            mOnClickStarListeners.onCheckStar(item);
        }
        
        @Override
        public void onUnCheckStar(Property item) {
            
            mOnClickStarListeners.onUnCheckStar(item);
        }
    };
    
    public FavoriteAdapter() {
    
    }
    
    @Override
    protected ItemPropertyBinding createBinding(ViewGroup parent) {
        ItemPropertyBinding itemPropertyBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_property, parent, false);
        return itemPropertyBinding;
    }
    
    @Override
    protected void bind(ItemPropertyBinding binding, Property item) {
        binding.imageRoom.setImageDrawable(null);
    
        if (item.getImages().size() > 0) {
            int placeHolder = ImageLoader.randomPlaceHolder();
            ImageLoader
                .load(binding.imageRoom, EndPoint.BASE_URL + item.getImages().get(0).getUrl(),
                    new RequestOptions()
                        .placeholder(placeHolder)
                        .error(placeHolder));
        }
        binding.setProperty(item);
        binding.setHandler(this);
        binding.txtAddress.setText(TextUtils.formatAddress(item.getAddress()));
        
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
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    public void setOnClickStarListeners(
        OnClickStarListener onClickStarListeners) {
        mOnClickStarListeners = onClickStarListeners;
    }
    
    public void onClickFavorite(View view, Property property) {
        ((StarView) view).addOnClick(property);
    }
    
    public void onClickProperty(Property property) {
        Timber.tag(TAG).d("Property: " + property.getAddress());
    }
    
    public interface OnClickStarListener {
        
        void onCheckStar(Property property);
        
        void onUnCheckStar(Property property);
    }
    
}
