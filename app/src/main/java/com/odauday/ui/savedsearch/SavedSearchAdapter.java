package com.odauday.ui.savedsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.odauday.R;
import com.odauday.databinding.ItemSavedSearchBinding;
import com.odauday.model.Search;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.view.NotificationView;
import com.odauday.ui.view.NotificationView.OnClickNotificationListener;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/10/2018.
 */

public class SavedSearchAdapter extends BaseAdapter<Search, ItemSavedSearchBinding> {
    
    private static final String TAG = SavedSearchAdapter.class.getSimpleName();
    NotificationView.OnClickNotificationListener<Search> mSearchOnClickNotificationListener = new OnClickNotificationListener<Search>() {
        @Override
        public void onNotification(Search item) {
            Timber.tag(TAG).d("On: " + item.getName());
        }
        
        @Override
        public void offNotification(Search item) {
            Timber.tag(TAG).d("Off: " + item.getName());
        }
    };
    private PopupMenu mPopupMenu;
    
    private OnClickRemoveSavedSearches mOnClickRemoveSavedSearches;
    
    public SavedSearchAdapter() {
    
    }
    
    @Override
    protected ItemSavedSearchBinding createBinding(ViewGroup parent) {
        ItemSavedSearchBinding itemSavedSearchBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_saved_search, parent, false);
        return itemSavedSearchBinding;
    }
    
    @Override
    protected void bind(ItemSavedSearchBinding binding, Search item) {
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
    
    public void setOnClickRemoveSavedSearches(
        OnClickRemoveSavedSearches onClickRemoveSavedSearches) {
        mOnClickRemoveSavedSearches = onClickRemoveSavedSearches;
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
        mOnClickRemoveSavedSearches.onClickRemoveSavedSearches(search);
    }
    
    public void onClickNotification(View view, Search search) {
        ((NotificationView) view)
            .setOnClickNotificationListener(mSearchOnClickNotificationListener);
        ((NotificationView) view).addOnClick(search);
    }
    
    public void onClickSavedSearch(Search search) {
        Timber.tag(TAG).d("Click: " + search.getName());
    }
    
    public interface OnClickRemoveSavedSearches {
        
        void onClickRemoveSavedSearches(Search search);
    }
}
