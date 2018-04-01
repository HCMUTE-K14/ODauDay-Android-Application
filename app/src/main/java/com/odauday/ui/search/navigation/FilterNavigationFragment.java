package com.odauday.ui.search.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;
import com.odauday.R;
import com.odauday.databinding.FragmentFilterBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 4/1/18.
 */

public class FilterNavigationFragment extends BaseMVVMFragment<FragmentFilterBinding> {
    
    //====================== Variable =========================//
    public static final String TAG = FilterNavigationFragment.class.getSimpleName();
    
    private boolean mIsShowMoreOptions = false;
    
    //====================== Constructor =========================//
    public static FilterNavigationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FilterNavigationFragment fragment = new FilterNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    //====================== Override Base Method =========================//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFilterView();
        resetMoreOptions();
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    //====================== ViewBinding method =========================//
    //====================== Contract method =========================//
    //====================== Helper method =========================//
    
    private void initFilterView() {
        mBinding.get().scrollView.setOnTouchListener(null);
        mBinding.get().filterSearchType.setListener(searchType -> {
        
        });
        
        mBinding.get().filterLocation.setTextHeader(getString(R.string.txt_filter_location));
        mBinding.get().filterLocation.setTextValue(getString(R.string.txt_map_area));
        
        mBinding.get().filterPrice.setTextHeader(getString(R.string.txt_filter_price));
        mBinding.get().filterSize.setTextHeader(getString(R.string.txt_filter_size));
        mBinding.get().filterPropertyType
                  .setTextHeader(getString(R.string.txt_filter_property_type));
        mBinding.get().filterBedrooms.setTextHeader(getString(R.string.txt_filter_num_of_bedroom));
        mBinding.get().filterBathRooms
                  .setTextHeader(getString(R.string.txt_filter_num_of_bathroom));
        mBinding.get().filterParking.setTextHeader(getString(R.string.txt_filter_num_of_parking));
        mBinding.get().filterTag.setTextHeader(getString(R.string.txt_filter_tags));
        
        mBinding.get().btnMoreOptions.setOnClickListener(view -> toggleMoreOptions());
    }
    
    
    private void toggleMoreOptions() {
        showAdvancedOptions(mIsShowMoreOptions);
        mBinding.get().btnMoreOptions.postDelayed((Runnable) () -> {
            mBinding.get().scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }, 250);
    }
    
    private void showAdvancedOptions(boolean show) {
        mIsShowMoreOptions = !show;
        mBinding.get().filterAdvanceOption.setVisibility(show ? View.VISIBLE : View.GONE);
        mBinding.get().btnMoreOptions.setText(show ? R.string.txt_filter_less_options
                  : R.string.txt_filter_more_options);
    }
    
    private void resetMoreOptions() {
        this.mIsShowMoreOptions = false;
        mBinding.get().btnMoreOptions.setText(R.string.txt_filter_more_options);
        mBinding.get().filterAdvanceOption.setVisibility(View.GONE);
    }
    
    public interface OnRefineSearch {
        
        void onCompleteRefineSearch(SearchCriteria searchCriteria);
    }
}
