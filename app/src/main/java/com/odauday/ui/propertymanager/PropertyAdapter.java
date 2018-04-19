package com.odauday.ui.propertymanager;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import com.odauday.R;
import com.odauday.config.Type;
import com.odauday.databinding.ItemPropertyManagerBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.base.BaseViewHolder;
import com.odauday.ui.propertymanager.status.Status;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class PropertyAdapter extends BaseAdapter<Property,ItemPropertyManagerBinding> {
    
    private static final String TAG=PropertyAdapter.class.getSimpleName();
    private PopupMenu mPopupMenu;
    private OnClickMenuListener mOnClickMenuListener;
    private Filter mFilter=new ItemFilter();
    private List<Property> mDisplayProperty=new ArrayList<>();
    @Override
    protected ItemPropertyManagerBinding createBinding(ViewGroup parent) {
        ItemPropertyManagerBinding itemPropertyManagerBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.item_property_manager,parent,false);
        return itemPropertyManagerBinding;
    }
    
    @Override
    public void onBindViewHolder(BaseViewHolder<ItemPropertyManagerBinding> holder, int position) {
        bind(holder.mBinding, mDisplayProperty.get(position));
        holder.mBinding.executePendingBindings();
    }
    
    @Override
    protected void bind(ItemPropertyManagerBinding binding, Property item) {
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
    
    @Override
    public void setData(List<Property> _data) {
        if(data==null){
            data=new ArrayList<>();
        }
        data.clear();
        mDisplayProperty.clear();
        data.addAll(_data);
        mDisplayProperty.addAll(_data);
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount() {
        return mDisplayProperty==null?0:mDisplayProperty.size();
    }
    
    public void onClickMoreMenu(View view,Property property){
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.popup_menu_action_manager_property, mPopupMenu.getMenu());
        
        if(property.getStatus().equals(Status.ENDED)){
            mPopupMenu.getMenu().removeItem(R.id.mark_the_end);
            mPopupMenu.getMenu().removeItem(R.id.edit);
            mPopupMenu.getMenu().removeItem(R.id.add_extend_time);
        }
        if(!property.getStatus().equals(Status.ENDED)){
            mPopupMenu.getMenu().removeItem(R.id.reuse);
        }
        mPopupMenu.setOnMenuItemClickListener(item -> {
            onHandlerItem(item,property);
            return false;
        });
        mPopupMenu.show();
    }
    private void onHandlerItem(MenuItem item, Property property) {
       
        switch (item.getItemId()){
            case R.id.edit:
                mOnClickMenuListener.editProperty(property);
                break;
            case R.id.delete:
                mOnClickMenuListener.deleteProperty(property);
                break;
            case R.id.mark_the_end:
                mOnClickMenuListener.markTheEndProperty(property);
                break;
            case R.id.add_extend_time:
                mOnClickMenuListener.addExtendsTime(property);
                break;
            case R.id.reuse:
                mOnClickMenuListener.reuseProperty(property);
                break;
                default:
                    break;
        }
        
    }
    
    public void setOnClickMenuListener(
        OnClickMenuListener onClickMenuListener) {
        mOnClickMenuListener = onClickMenuListener;
    }
    
    public interface OnClickMenuListener{
        void editProperty(Property property);
        void deleteProperty(Property property);
        void markTheEndProperty(Property property);
        void addExtendsTime(Property property);
        void reuseProperty(Property property);
    }
    public void filter(String text){
        if(TextUtils.isEmpty(text)){
            this.mDisplayProperty.clear();
            this.mDisplayProperty.addAll(data);
            notifyDataSetChanged();
            return;
            
        }
        this.mFilter.filter(text);
    }
    private class ItemFilter extends Filter {
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            
            String filterString = constraint.toString().toLowerCase();
            
            FilterResults results = new FilterResults();
            
            final ArrayList<Property> tempFilterList = new ArrayList<>();
            
            for (Property property : data) {
                String name = property.getName().toLowerCase();
                if (name.contains(filterString)) {
                    tempFilterList.add(property);
                }
            }
            
            results.values = tempFilterList;
            results.count = tempFilterList.size();
            
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDisplayProperty.clear();
            mDisplayProperty = (ArrayList<Property>) results.values;
            notifyDataSetChanged();
        }
    }
}
