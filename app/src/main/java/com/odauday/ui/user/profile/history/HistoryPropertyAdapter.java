package com.odauday.ui.user.profile.history;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.databinding.ItemHistoryBinding;
import com.odauday.model.HistoryDetail;
import com.odauday.ui.base.BaseAdapter;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/29/18.
 */
public class HistoryPropertyAdapter extends BaseAdapter<HistoryDetail, ItemHistoryBinding> {
    
    private OnClickHistory mOnClickHistory;
    
    public HistoryPropertyAdapter(OnClickHistory onClickHistory) {
        this.mOnClickHistory = onClickHistory;
    }
    
    @Override
    protected ItemHistoryBinding createBinding(ViewGroup parent) {
        return DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_history, parent, false);
    }
    
    public void reset() {
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        notifyDataSetChanged();
    }
    
    @Override
    public void setData(List<HistoryDetail> _data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    public void setOnClickHistory(
        OnClickHistory onClickHistory) {
        mOnClickHistory = onClickHistory;
    }
    
    @Override
    protected void bind(ItemHistoryBinding binding, HistoryDetail item) {
        binding.container.setOnClickListener(view -> {
            if (mOnClickHistory != null) {
                mOnClickHistory.onClickHistory(item);
            }
        });
        binding.address.setText(item.getAddress());
        if (item.getImages() != null) {
            int placeHolder = ImageLoader.randomPlaceHolder();
            RequestOptions options = new RequestOptions();
            String url = "";
            if (!item.getImages().isEmpty()) {
                url = TextUtils.getImageUrl(item.getImages().get(0).getUrl());
            }
            
            ImageLoader
                .load(binding.image, url, options
                    .placeholder(placeHolder)
                    .error(placeHolder));
        }
        String bedBathParkingText = binding.bedBathPark.getContext()
            .getString(R.string.txt_display_bedroom_bathroom_parking, item.getNumOfBedrooms(),
                item.getNumOfBathrooms(), item.getNumOfParkings());
        
        binding.bedBathPark.setText(bedBathParkingText);
        
        binding.dateViewed.setText(TextUtils.formatDateForDisplayHistory(item.getDateCreated()));
    }
    
    @Override
    protected boolean areItemsTheSame(HistoryDetail oldItem, HistoryDetail newItem) {
        return false;
    }
    
    @Override
    protected boolean areContentsTheSame(HistoryDetail oldItem, HistoryDetail newItem) {
        return false;
    }
    
    public interface OnClickHistory {
        
        void onClickHistory(HistoryDetail item);
    }
}
