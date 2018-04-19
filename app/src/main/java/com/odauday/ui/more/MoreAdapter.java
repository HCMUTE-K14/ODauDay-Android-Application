package com.odauday.ui.more;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemMoreMenuBinding;
import com.odauday.ui.base.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/19/2018.
 */

public class MoreAdapter extends BaseAdapter<MenuItemMore, ItemMoreMenuBinding> {
    private static final String TAG=MoreAdapter.class.getSimpleName();
    private OnClickMenuMoreListener mOnClickMenuMoreListener;
    @Override
    protected ItemMoreMenuBinding createBinding(ViewGroup parent) {
        ItemMoreMenuBinding itemMoreMenuBinding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_more_menu,parent,false);
        return itemMoreMenuBinding;
    }
    
    @Override
    protected void bind(ItemMoreMenuBinding binding, MenuItemMore item) {
        binding.setItem(item);
        binding.setHandler(this);
    }
    @Override
    public void setData(List<MenuItemMore> _data) {
        if(data==null){
            data=new ArrayList<>();
        }
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    @Override
    protected boolean areItemsTheSame(MenuItemMore oldItem, MenuItemMore newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    @Override
    protected boolean areContentsTheSame(MenuItemMore oldItem, MenuItemMore newItem) {
        return oldItem.equals(newItem);
    }
    
    public void setOnClickMenuMoreListener(
        OnClickMenuMoreListener onClickMenuMoreListener) {
        mOnClickMenuMoreListener = onClickMenuMoreListener;
    }
    
    public void onClickMenuItem(MenuItemMore item){
        Timber.tag(TAG).d("Click:"+item.getName());
        if(mOnClickMenuMoreListener!=null){
            mOnClickMenuMoreListener.onClickItemMenu(item);
        }
        
    }
    public interface OnClickMenuMoreListener{
        void onClickItemMenu(MenuItemMore item);
    }
}
