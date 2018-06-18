package com.odauday.ui.admin.usermanager;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.odauday.R;
import com.odauday.data.remote.user.model.Status;
import com.odauday.databinding.ItemUserManagerBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 5/12/2018.
 */

public class UserManagerAdapter extends BaseAdapter<User,ItemUserManagerBinding> {
    private PopupMenu mPopupMenu;
    private OnClickActionListener mOnClickActionListener;
    private OnClickItemUserListener mOnClickItemUserListener;
    @Override
    protected ItemUserManagerBinding createBinding(ViewGroup parent) {
        ItemUserManagerBinding itemUserManagerBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_user_manager, parent, false);
        return itemUserManagerBinding;
    }
    
    @Override
    protected void bind(ItemUserManagerBinding binding, User item) {
        binding.avatar.setImageDrawable(null);
        binding.setUser(item);
        binding.setHandler(this);
    }
    @Override
    public void update(List<User> newData) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.addAll(newData);
        notifyDataSetChanged();
    }
    
    @Override
    protected boolean areItemsTheSame(User oldItem, User newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(User oldItem, User newItem) {
        return oldItem.equals(newItem);
    }
    
    
    public void setOnClickActionListener(
        OnClickActionListener onClickActionListener) {
        mOnClickActionListener = onClickActionListener;
    }
    
    public void setOnClickItemUserListener(
        OnClickItemUserListener onClickItemUserListener) {
        mOnClickItemUserListener = onClickItemUserListener;
    }
    
    public void clearData(){
        if(data!=null){
            data.clear();
        }
    }
    public void changeStatusItem(User user, String status){
        int index=data.indexOf(user);
        if(index>-1){
            data.get(index).setStatus(status);
            notifyDataSetChanged();
        }
    }
    public void removeItem(User user){
        data.remove(user);
        notifyDataSetChanged();
    }
    public void onClickAction(View view,User user){
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.menu_action_user_manager, mPopupMenu.getMenu());
    
        if (user.getStatus().equals(Status.ACTIVE)||user.getStatus().equals(Status.PENDING)) {
            mPopupMenu.getMenu().removeItem(R.id.active);
        }
        if (user.getStatus().equals(Status.DISABLED)) {
            mPopupMenu.getMenu().removeItem(R.id.ban);
        }
        mPopupMenu.setOnMenuItemClickListener(item -> {
            onHandlerItem(item, user);
            return false;
        });
        mPopupMenu.show();
    }
    
    private void onHandlerItem(MenuItem item, User user) {
        switch (item.getItemId()){
            case R.id.active:
                if(mOnClickActionListener!=null){
                    mOnClickActionListener.Active(user);
                }
                break;
            case R.id.ban:
                if(mOnClickActionListener!=null){
                    mOnClickActionListener.Ban(user);
                }
                break;
                default:break;
        }
    }
    public void onClickItemUser(User user){
        if(mOnClickItemUserListener!=null){
            mOnClickItemUserListener.onClickItemUser(user);
        }
    }
    
    public List<User> getData() {
        return data;
    }
    
    public interface OnClickItemUserListener{
        void onClickItemUser(User user);
    }
    public interface OnClickActionListener{
        void Active(User user);
        void Ban(User user);
    }
}
