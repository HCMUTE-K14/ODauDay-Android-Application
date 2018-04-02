package com.odauday.ui.search.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.databinding.FragmentFilterBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.view.FilterPickerDialog;
import com.odauday.ui.search.common.view.FilterPickerDialog.OnCompletePickerListener;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 4/1/18.
 */

public class FilterNavigationFragment extends BaseMVVMFragment<FragmentFilterBinding> implements
                                                                                      OnCompletePickerListener {
    
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
    
    //====================== Implement method =========================//
    
    @Override
    public void onCompletePicker(int requestCode, int pickedValueFrom, int pickedValueTo) {
        
        FilterOption option = FilterOption.getByRequestCode(requestCode);
        switch (option) {
            case PRICE:
            case SIZE:
            case BEDROOMS:
            case BATHROOMS:
            case PARKING:
                Toast.makeText(getContext(), pickedValueFrom + "-" + pickedValueTo,
                          Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
    
    //====================== Contract method =========================//
    //====================== Helper method =========================//
    
    private void initFilterView() {
        mBinding.get().scrollView.setOnTouchListener(null);
        mBinding.get().filterSearchType.setListener(searchType -> {
        
        });
        
        mBinding.get().filterLocation.setTextHeader(getString(R.string.txt_filter_location));
        mBinding.get().filterLocation.setTextValue(getString(R.string.txt_map_area));
        
        mBinding.get().filterPrice.setTextHeader(getString(R.string.txt_filter_price));
        mBinding.get().filterPrice.setListener(() -> {
            showDialogPicker(FilterOption.PRICE);
        });
        
        mBinding.get().filterSize.setTextHeader(getString(R.string.txt_filter_size));
        mBinding.get().filterSize.setListener(() -> {
            showDialogPicker(FilterOption.SIZE);
        });
        mBinding.get().filterPropertyType
                  .setTextHeader(getString(R.string.txt_filter_property_type));
        
        mBinding.get().filterBedrooms.setTextHeader(getString(R.string.txt_filter_num_of_bedroom));
        mBinding.get().filterBedrooms.setListener(() -> {
            showDialogPicker(FilterOption.BEDROOMS);
        });
        
        mBinding.get().filterBathRooms
                  .setTextHeader(getString(R.string.txt_filter_num_of_bathroom));
        mBinding.get().filterBathRooms.setListener(() -> {
            showDialogPicker(FilterOption.BATHROOMS);
        });
        mBinding.get().filterParking.setTextHeader(getString(R.string.txt_filter_num_of_parking));
        mBinding.get().filterParking.setListener(() -> {
            showDialogPicker(FilterOption.PARKING);
        });
        
        mBinding.get().filterTag.setTextHeader(getString(R.string.txt_filter_tags));
        
        mBinding.get().btnMoreOptions.setOnClickListener(view -> {
            toggleMoreOptions();
        });
    }
    
    private void showDialogPicker(FilterOption option) {
        FilterPickerDialog pd;
        String title;
        
        switch (option) {
            case PRICE:
                title = getString(R.string.txt_filter_price);
                //SERACH TYPE ==> RENT OR BUY
                pd = initDialogPicker(true, title, R.array.price_buy,
                          0, 0, 0, true);
                break;
            case SIZE:
                title = getString(R.string.txt_filter_size);
                pd = initDialogPicker(false, title, R.array.land_size_count_int,
                          R.array.land_size_count_string, 0, 0, true);
                break;
            case BEDROOMS:
                title = getString(R.string.txt_filter_num_of_bedroom);
                pd = initDialogPicker(false, title, R.array.bedroom_count_int,
                          R.array.bedroom_count_string, 0, 0, true);
                break;
            case BATHROOMS:
                title = getString(R.string.txt_filter_num_of_bathroom);
                pd = initDialogPicker(false, title, R.array.bathroom_count_int,
                          R.array.bathroom_count_string, 0, 0, true);
                break;
            case PARKING:
                title = getString(R.string.txt_filter_num_of_parking);
                pd = initDialogPicker(false, title, R.array.parking_count_int,
                          R.array.parking_count_string, 0, 0, false);
                break;
            default:
                pd = null;
                break;
        }
        if (pd == null || getFragmentManager() == null) {
            return;
        }
        pd.setTargetFragment(this, option.getRequestCode());
        pd.show(getFragmentManager(), option.getTag());
    }
    
    private FilterPickerDialog initDialogPicker(boolean hasCurrency, String title, int intValuesRes,
              int displayValuesRes, int from,
              int to, boolean hasToWheel) {
        return new FilterPickerDialog.Builder(hasCurrency)
                  .setTitle(title)
                  .setValueRes(intValuesRes)
                  .setDisplayValueRes(displayValuesRes)
                  .setSelectedFrom(from)
                  .setSelectedTo(to)
                  .setHasToWheel(hasToWheel)
                  .build();
    }
    
    private void toggleMoreOptions() {
        mIsShowMoreOptions = !mIsShowMoreOptions;
        showAdvancedOptions(mIsShowMoreOptions);
        
    }
    
    private void showAdvancedOptions(boolean show) {
        mBinding.get().filterAdvanceOption.setVisibility(show ? View.VISIBLE : View.GONE);
        
        mBinding.get().btnMoreOptions.postDelayed((Runnable) () -> {
            mBinding.get().scrollView
                      .fullScroll(show ? ScrollView.FOCUS_DOWN : ScrollView.FOCUS_UP);
        }, 250);
        mBinding.get().btnMoreOptions.setText(show ? R.string.txt_filter_less_options
                  : R.string.txt_filter_more_options);
    }
    
    private void resetMoreOptions() {
        mIsShowMoreOptions = false;
        mBinding.get().btnMoreOptions.setText(R.string.txt_filter_more_options);
        mBinding.get().filterAdvanceOption.setVisibility(View.GONE);
    }
    
    public interface OnRefineSearch {
        
        void onCompleteRefineSearch(SearchCriteria searchCriteria);
    }
}
