package com.odauday.ui.admin.propertymanager;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.odauday.R;
import com.odauday.api.EndPoint;
import com.odauday.databinding.ItemConfirmPropertyBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.propertymanager.status.Status;
import com.odauday.utils.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 5/9/2018.
 */

public class ConfirmPropertyAdapter extends BaseAdapter<Property, ItemConfirmPropertyBinding> {
    private static final String TAG=ConfirmPropertyAdapter.class.getSimpleName();
    private PopupMenu mPopupMenu;
    private OnClickMenuListener mOnClickMenuListener;
    private onClickItemPropertyListener mOnClickItemPropertyListener;
    @Override
    protected ItemConfirmPropertyBinding createBinding(ViewGroup parent) {
        ItemConfirmPropertyBinding itemConfirmPropertyBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_confirm_property, parent, false);
        return itemConfirmPropertyBinding;
    }
    
    @Override
    protected void bind(ItemConfirmPropertyBinding binding, Property item) {
        binding.txtName.setEllipsize(TextUtils.TruncateAt.END);
        binding.txtName.setMaxLines(2);
        binding.imageProperty.setImageDrawable(null);
       /* if(item.getImages()!=null&&item.getImages().size()>0){
            ImageLoader.load(binding.imageProperty.getContext(),binding.imageProperty,
                EndPoint.BASE_URL+item.getImages().get(0).getUrl());
        }*/
        binding.setProperty(item);
        binding.setHandler(this);
    }
    
    @Override
    protected boolean areItemsTheSame(Property oldItem, Property newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(Property oldItem, Property newItem) {
        return oldItem.equals(newItem);
    }
    public void clearData(){
        data.clear();
    }
    public void removeItem(Property property){
        data.remove(property);
        notifyDataSetChanged();
    }
    public void changeStatusItem(Property property, String status){
        int index=data.indexOf(property);
        if(index>-1){
            data.get(index).setStatus(status);
            notifyDataSetChanged();
        }
    }
    @Override
    public void update(List<Property> newData) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.addAll(newData);
        notifyDataSetChanged();
    }
    
    public void setOnClickMenuListener(
        OnClickMenuListener onClickMenuListener) {
        mOnClickMenuListener = onClickMenuListener;
    }
    
    public void onClickMoreMenu(View view, Property property) {
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.menu_action_admin, mPopupMenu.getMenu());
    
        if (property.getStatus().equals(Status.ACTIVE)) {
            mPopupMenu.getMenu().removeItem(R.id.confirm);
        }
        if (property.getStatus().equals(Status.EXPIRED)) {
            mPopupMenu.getMenu().removeItem(R.id.mark_the_end);
            mPopupMenu.getMenu().removeItem(R.id.confirm);
        }
        mPopupMenu.setOnMenuItemClickListener(item -> {
            onHandlerItem(item, property);
            return false;
        });
        mPopupMenu.show();
    }
    
    private void onHandlerItem(MenuItem item, Property property) {
        switch (item.getItemId()){
            case R.id.delete:
                mOnClickMenuListener.deleteProperty(property);
                break;
            case R.id.mark_the_end:
                mOnClickMenuListener.markTheEndProperty(property);
                break;
            case R.id.confirm:
                mOnClickMenuListener.confirmProperty(property);
                break;
        }
    }
    
    public void setOnClickItemPropertyListener(
        onClickItemPropertyListener onClickItemPropertyListener) {
        mOnClickItemPropertyListener = onClickItemPropertyListener;
    }
    
    public void onClickProperty(Property property){
        if(mOnClickItemPropertyListener!=null){
            mOnClickItemPropertyListener.onClickItemProperty(property);
        }
    }
    
    public List<Property> getData() {
        return data;
    }
    
    public interface OnClickMenuListener {
        
        void deleteProperty(Property property);
        
        void markTheEndProperty(Property property);
        
        void confirmProperty(Property property);
    }
    public interface onClickItemPropertyListener{
        void onClickItemProperty(Property property);
    }
    
}
