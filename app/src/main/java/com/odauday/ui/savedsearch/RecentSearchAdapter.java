package com.odauday.ui.savedsearch;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.ItemRecentSearchesBinding;
import com.odauday.model.Search;
import com.odauday.ui.base.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by kunsubin on 4/10/2018.
 */

public class RecentSearchAdapter extends BaseAdapter<Search, ItemRecentSearchesBinding> {
    
    private static final String TAG = RecentSearchAdapter.class.getSimpleName();
    private PopupMenu mPopupMenu;
    private OnClickRemoveRecentSearches mOnClickRemoveRecentSearches;
    
    public RecentSearchAdapter() {
    
    }
    
    @Override
    protected ItemRecentSearchesBinding createBinding(ViewGroup parent) {
        ItemRecentSearchesBinding itemRecentSearchesBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_recent_searches, parent, false);
        return itemRecentSearchesBinding;
    }
    
    @Override
    protected void bind(ItemRecentSearchesBinding binding, Search item) {
        binding.setSearch(item);
        binding.setHandler(this);
    }
    
    @Override
    protected boolean areItemsTheSame(Search oldItem, Search newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Search oldItem, Search newItem) {
        return oldItem.equals(newItem);
    }
    
    @Override
    public void setData(List<Search> _data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    public void setOnClickRemoveRecentSearches(
        OnClickRemoveRecentSearches onClickRemoveRecentSearches) {
        mOnClickRemoveRecentSearches = onClickRemoveRecentSearches;
    }
    
    public void onClickMoreMenu(View view, Search search) {
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.popup_more_menu_saved_search, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(item -> {
            handlerSearch(search);
            return false;
        });
        mPopupMenu.show();
    }
    
    private void handlerSearch(Search search) {
        mOnClickRemoveRecentSearches.ClickRemoveRecentSearch(search);
    }
    
    public void onClickRecentSearch(Search search) {
        EventBus.getDefault().post(new OnClickSavedSearch(search));
    }
    
    public interface OnClickRemoveRecentSearches {
        
        void ClickRemoveRecentSearch(Search search);
    }
}

