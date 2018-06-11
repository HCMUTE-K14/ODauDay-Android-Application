package com.odauday.ui.alert;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.jakewharton.rxbinding2.view.ViewGroupHierarchyChildViewAddEvent;
import com.odauday.R;
import com.odauday.databinding.ItemNotificationBinding;
import com.odauday.ui.alert.service.Notification;
import com.odauday.ui.base.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 6/4/2018.
 */

public class AlertAdapter extends BaseAdapter<Notification,ItemNotificationBinding> {
    private PopupMenu mPopupMenu;
    private OnClickActionNotificationListener mOnClickActionNotificationListener;
    @Override
    protected ItemNotificationBinding createBinding(ViewGroup parent) {
        ItemNotificationBinding itemNotificationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_notification, parent, false);
        return itemNotificationBinding;
    }
    
    @Override
    protected void bind(ItemNotificationBinding binding, Notification item) {
        binding.setNotification(item);
        binding.setHandler(this);
    }
    
    @Override
    protected boolean areItemsTheSame(Notification oldItem, Notification newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Notification oldItem, Notification newItem) {
        return oldItem.equals(newItem);
    }
    public void removeItemData(int index){
        data.remove(index);
        notifyDataSetChanged();
    }
    public void removeItemData(Notification notification){
        data.remove(notification);
        notifyDataSetChanged();
    }
    public Notification getItem(int position){
        return data.get(position);
    }
    public List<Notification> getData(){
        return data;
    }
    public void clearData(){
        data.clear();
        notifyDataSetChanged();
    }
    
    public void setOnClickActionNotificationListener(
        OnClickActionNotificationListener onClickActionNotificationListener) {
        mOnClickActionNotificationListener = onClickActionNotificationListener;
    }
    
    public void onClickAction(View view, Notification notification){
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.popup_dismiss_notification, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.dismiss:
                    mOnClickActionNotificationListener.onClickAction(notification);
                    break;
                    default:break;
            }
            return false;
        });
        mPopupMenu.show();
    }
    public void putNotification(Notification notification){
        if(data==null){
            data=new ArrayList<>();
        }
        data.add(0,notification);
        notifyDataSetChanged();
    }
    public interface OnClickActionNotificationListener{
        void onClickAction(Notification notification);
    }
}
