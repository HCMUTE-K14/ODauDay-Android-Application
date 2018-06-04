package com.odauday.ui.search.listview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.databinding.ItemForListPropertyBinding;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.search.listview.GalleryViewPagerAdapter.GalleryViewPagerListener;
import com.odauday.ui.view.StarView.OnClickStarListener;
import com.odauday.ui.view.StarView.STATUS;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

/**
 * Created by infamouSs on 6/1/18.
 */
@SuppressWarnings({"unchecked", "CheckResult"})
public class ListViewAdapter extends
                             BaseAdapter<PropertyResultEntry, ItemForListPropertyBinding> implements
                                                                                          GalleryViewPagerListener {
    
    private Context mContext;
    
    private OnClickStarListener mOnClickStarListener;
    
    
    public ListViewAdapter(Context context) {
        mContext = context;
        
    }
    
    public List<PropertyResultEntry> getData() {
        return this.data;
    }
    
    @Override
    protected ItemForListPropertyBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_for_list_property, parent, false);
    }
    
    
    public void setOnClickStarListener(OnClickStarListener onClickStarListener) {
        mOnClickStarListener = onClickStarListener;
    }
    
    @Override
    protected void bind(ItemForListPropertyBinding binding, PropertyResultEntry item) {
        OnClickListener onClickListener = v -> openPropertyDetailActivity(item);
        GalleryViewPagerAdapter galleryViewPagerAdapter = new GalleryViewPagerAdapter(mContext,
            this, onClickListener);
        galleryViewPagerAdapter.setData(item);
        binding.galleryViewPager.setAdapter(galleryViewPagerAdapter);
        binding.galleryViewPager.postDelayed(() -> {
            binding.galleryViewPager.setCurrentItem(1);
        }, 20);
        binding.address.setText(TextUtils.formatAddress(item.getAddress()));
        binding.favorite.setSTATUS(item.isFavorite() ? STATUS.UN_CHECK : STATUS.CHECK);
        binding.price.setText(TextUtils.formatIntToCurrency((float) item.getPrice(), true));
        TextView type = binding.propertyType.findViewById(R.id.type);
        
        type.setText(item.getSearchType().equals("1") ? R.string.txt_buy : R.string.txt_rent);
        
        showDataFeature(binding.bedsContainer, binding.beds, item.getNumOfBedRooms());
        showDataFeature(binding.bathsContainer, binding.baths, item.getNumOfBathRooms());
        showDataFeature(binding.parkingsContainer, binding.parkings, item.getNumOfBedRooms());
        
        updateViewHolder();
        
        binding.vitalContainer.setOnClickListener(view -> {
            Timber.d(item.getId());
            openPropertyDetailActivity(item);
        });
        binding.favorite.setOnClickStarListener(mOnClickStarListener);
        RxView.clicks(binding.favorite)
            .throttleLast(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success -> {
                binding.favorite.addOnClick(item);
            });
    }
    
    
    private void updateViewHolder() {
        
        getBinding().address.setTextColor(Color.parseColor("#666666"));
    }
    
    private void showDataFeature(View container, TextView textView, int value) {
        if (value > 0) {
            textView.setText(String.valueOf(value));
            ViewUtils.showHideView(container, true);
        } else {
            ViewUtils.showHideView(container, false);
        }
    }
    
    @Override
    protected boolean areItemsTheSame(PropertyResultEntry oldItem, PropertyResultEntry newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
    
    @Override
    protected boolean areContentsTheSame(PropertyResultEntry oldItem, PropertyResultEntry newItem) {
        return oldItem.equals(newItem);
    }
    
    @Override
    public void showHints() {
        getBinding().hints.setVisibility(View.VISIBLE);
        
        getBinding().hints.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
    }
    
    @Override
    public void hideHints() {
        getBinding().hints.setVisibility(View.GONE);
        
        getBinding().hints.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out));
    }
    
    private void openPropertyDetailActivity(PropertyResultEntry entry) {
        if (entry != null) {
            PropertyDetail propertyDetail = new PropertyDetail();
            propertyDetail.setId(entry.getId());
            propertyDetail.setFavorite(entry.isFavorite());
            
            Intent intent = new Intent(mContext, PropertyDetailActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, propertyDetail);
            
            mContext.startActivity(intent);
        }
    }
}
