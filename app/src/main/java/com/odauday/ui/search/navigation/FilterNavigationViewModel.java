package com.odauday.ui.search.navigation;

import android.view.View;
import com.odauday.R;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.MinMaxObject;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.SearchType;
import com.odauday.ui.search.common.view.FilterNumberPickerDialog;
import com.odauday.ui.search.common.view.propertydialog.PropertyTypeDialog;
import com.odauday.ui.search.common.view.tagdialog.TagTypeDialog;
import com.odauday.utils.TextUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class FilterNavigationViewModel extends BaseViewModel {
    
    private FilterNavigationFragment mFragment;
    
    private boolean mIsShowMoreOptions = false;
    
    @Inject
    public FilterNavigationViewModel() {
    
    }
    
    public FilterNavigationFragment getFragment() {
        return mFragment;
    }
    
    public void setFragment(FilterNavigationFragment fragment) {
        mFragment = fragment;
    }
    
    public void showTypePickerDialog(FilterOption option) {
        if (mFragment.getFragmentManager() == null) {
            return;
        }
        BaseDialogFragment dialog;
        switch (option) {
            case PROPERTY_TYPE:
                List<Integer> selectedPropertyType = PropertyType
                          .convertToArrayInt(mFragment.getSearchCriteria().getPropertyType());
                dialog = PropertyTypeDialog
                          .newInstance(selectedPropertyType);
                break;
            case TAGS:
                List<Tag> selectedTag = mFragment.getSearchCriteria().getTags();
                
                List<Tag> recentTags = new ArrayList<>(Arrays.asList(new Tag("20", "Gym"),
                          new Tag("2", "Balcony / Deck")));
                
                dialog = TagTypeDialog
                          .newInstance(selectedTag, recentTags);
                break;
            default:
                dialog = null;
                break;
        }
        if (dialog == null) {
            return;
        }
        
        dialog.setTargetFragment(mFragment, option.getRequestCode());
        dialog.show(mFragment.getFragmentManager(), option.getTag());
    }
    
    public void showNumberPickerDialog(FilterOption option) {
        FilterNumberPickerDialog pd;
        String title;
        
        switch (option) {
            case PRICE:
                SearchType searchType = SearchType
                          .getByValue(mFragment.getSearchCriteria().getSearchType());
                
                title = TextUtils.build(mFragment.getString(R.string.txt_filter_price),
                          mFragment.getString(R.string.txt_filter_price_rate));
                
                MinMaxObject<Integer> priceFromTo = mFragment.getSearchCriteria().getPrice();
                
                if (searchType == SearchType.BUY) {
                    pd = initNumberPickerDialog(true, title, R.array.price_buy,
                              0, priceFromTo.getMin(), priceFromTo.getMax(), true);
                } else if (searchType == SearchType.RENT) {
                    pd = initNumberPickerDialog(true, title, R.array.price_rent,
                              0, priceFromTo.getMin(), priceFromTo.getMax(), true);
                } else {
                    pd = null;
                }
                
                break;
            case SIZE:
                title = mFragment.getString(R.string.txt_filter_size);
                MinMaxObject<Integer> landSize = mFragment.getSearchCriteria().getSize();
                
                pd = initNumberPickerDialog(false, title, R.array.land_size_count_int,
                          R.array.land_size_count_string, landSize.getMin(), landSize.getMax(),
                          true);
                break;
            case BEDROOMS:
                title = mFragment.getString(R.string.txt_filter_num_of_bedroom);
                MinMaxObject<Integer> bedrooms = mFragment.getSearchCriteria().getBedrooms();
                
                pd = initNumberPickerDialog(false, title, R.array.bedroom_count_int,
                          R.array.bedroom_count_string, bedrooms.getMin(), bedrooms.getMax(), true);
                break;
            case BATHROOMS:
                title = mFragment.getString(R.string.txt_filter_num_of_bathroom);
                MinMaxObject<Integer> bathrooms = mFragment.getSearchCriteria().getBathrooms();
                
                pd = initNumberPickerDialog(false, title, R.array.bathroom_count_int,
                          R.array.bathroom_count_string, bathrooms.getMin(), bathrooms.getMax(),
                          true);
                break;
            case PARKING:
                title = mFragment.getString(R.string.txt_filter_num_of_parking);
                MinMaxObject<Integer> parkings = mFragment.getSearchCriteria().getBathrooms();
                
                pd = initNumberPickerDialog(false, title, R.array.parking_count_int,
                          R.array.parking_count_string, parkings.getMin(), parkings.getMax(),
                          false);
                break;
            default:
                pd = null;
                break;
        }
        if (pd == null || mFragment.getFragmentManager() == null) {
            return;
        }
        pd.setTargetFragment(mFragment, option.getRequestCode());
        pd.show(mFragment.getFragmentManager(), option.getTag());
    }
    
    private FilterNumberPickerDialog initNumberPickerDialog(boolean hasCurrency, String title,
              int intValuesRes,
              int displayValuesRes, int from,
              int to, boolean hasToWheel) {
        return new FilterNumberPickerDialog.Builder(hasCurrency)
                  .setTitle(title)
                  .setValueRes(intValuesRes)
                  .setDisplayValueRes(displayValuesRes)
                  .setSelectedFrom(from)
                  .setSelectedTo(to)
                  .setHasToWheel(hasToWheel)
                  .build();
    }
    
    public void onSelectedSearchType(SearchType searchType) {
        if (mFragment.getBinding().get() == null) {
            return;
        }
        resetFilter();
        mFragment.getSearchCriteria().setSearchType(searchType.getValue());
        
        resetViewWhenChangeSearchType(searchType);
    }
    
    private void resetFilter() {
        
        mFragment.setSearchCriteria(new SearchCriteria());
        mFragment.getBinding().get().filterLocation.reset();
        mFragment.getBinding().get().filterPrice.reset();
        mFragment.getBinding().get().filterSize.reset();
        mFragment.getBinding().get().filterPropertyType.reset();
        mFragment.getBinding().get().filterBedrooms.reset();
        mFragment.getBinding().get().filterBathRooms.reset();
        mFragment.getBinding().get().filterParking.reset();
        mFragment.getBinding().get().filterTag.reset();
        
    }
    
    private void resetViewWhenChangeSearchType(SearchType searchType) {
        
        if (searchType == SearchType.ALL) {
            mFragment.getBinding().get().filterPrice.setVisibility(View.GONE);
        } else {
            if (searchType == SearchType.RENT) {
                mFragment.getBinding().get().filterPrice
                          .setTextHeader(mFragment.getString(R.string.txt_rent_per_month));
            } else {
                mFragment.getBinding().get().filterPrice
                          .setTextHeader(mFragment.getString(R.string.txt_filter_price));
            }
            mFragment.getBinding().get().filterPrice.setVisibility(View.VISIBLE);
        }
    }
    
    
    public void toggleMoreOptions(View view) {
        mIsShowMoreOptions = !mIsShowMoreOptions;
        
        showAdvancedOptions(mIsShowMoreOptions);
    }
    
    private void showAdvancedOptions(boolean show) {
        mFragment.getBinding().get().filterAdvanceOption
                  .setVisibility(show ? View.VISIBLE : View.GONE);
        mFragment.getBinding().get().btnMoreOptions.setText(show ? R.string.txt_filter_less_options
                  : R.string.txt_filter_more_options);
        mFragment.getBinding().get().filterAdvanceOption.postDelayed(() -> {
            mFragment.getBinding().get().scrollView
                      .fullScroll(show ? View.FOCUS_DOWN : View.FOCUS_UP);
        }, 500);
    }
    
    public void resetMoreOptions() {
        mIsShowMoreOptions = false;
        mFragment.getBinding().get().btnMoreOptions.setText(R.string.txt_filter_more_options);
        mFragment.getBinding().get().filterAdvanceOption.setVisibility(View.GONE);
    }
}
