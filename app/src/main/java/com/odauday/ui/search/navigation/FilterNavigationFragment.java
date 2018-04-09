package com.odauday.ui.search.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.databinding.FragmentFilterBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.TextAndMoreTextValue;
import com.odauday.ui.search.common.view.FilterNumberPickerDialog.OnCompletePickedNumberListener;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import com.odauday.ui.search.common.view.PickerMinMaxReturnObject;
import com.odauday.ui.search.common.view.tagdialog.TagChip;
import com.odauday.utils.TextUtils;
import com.odauday.viewmodel.BaseViewModel;
import com.pchmn.materialchips.model.ChipInterface;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/1/18.
 */

public class FilterNavigationFragment extends BaseMVVMFragment<FragmentFilterBinding> implements
                                                                                      OnCompletePickedNumberListener,
                                                                                      OnCompletePickedType {
    
    //====================== Variable =========================//
    public static final String TAG = FilterNavigationFragment.class.getSimpleName();
    
    @Inject
    FilterNavigationViewModel mFilterNavigationViewModel;
    
    private SearchCriteria mSearchCriteria;
    
    
    //====================== Constructor =========================//
    public static FilterNavigationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FilterNavigationFragment fragment = new FilterNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public static FilterNavigationFragment newInstance(SearchCriteria searchCriteria) {
        
        Bundle args = new Bundle();
        args.putParcelable(Constants.INTENT_EXTRA_CURRENT_SEARCH_CRITERIA, searchCriteria);
        FilterNavigationFragment fragment = new FilterNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    //====================== Override Base Method =========================//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchCriteria = getArguments()
                      .getParcelable(Constants.INTENT_EXTRA_CURRENT_SEARCH_CRITERIA);
            
        } else {
            mSearchCriteria = new SearchCriteria();
        }
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mFilterNavigationViewModel.setFragment(this);
        mBinding.get().setViewModel(mFilterNavigationViewModel);
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mFilterNavigationViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    //====================== Implement method =========================//
    
    @Override
    public void onCompletePickedNumber(int requestCode,
              PickerMinMaxReturnObject minMaxReturnObject) {
        Timber.tag(TAG).d("REQUEST_CODE:" + requestCode);
        Timber.tag(TAG).d("FROM:" + minMaxReturnObject.getValue().getMin());
        Timber.tag(TAG).d("TO:" + minMaxReturnObject.getValue().getMax());
        
        FilterOption option = FilterOption.getByRequestCode(requestCode);
        switch (option) {
            case PRICE:
                mSearchCriteria.setPrice(minMaxReturnObject.getValue());
                String textPrice = getMaxMinText(option, minMaxReturnObject);
                if (textPrice.split("-").length == 2) {
                    String[] textPricePart = textPrice.split("-");
                    mBinding.get().filterPrice.setTextValue(
                              TextUtils.build(
                                        getString(R.string.txt_from),
                                        " ",
                                        textPricePart[0].trim())
                    );
                    mBinding.get().filterPrice.setMoreValue(
                              TextUtils.build(
                                        getString(R.string.txt_to),
                                        " ",
                                        textPricePart[1].trim()
                              )
                    );
                    return;
                }
                mBinding.get().filterPrice.setMoreValue("");
                mBinding.get().filterPrice.setTextValue(textPrice);
                break;
            case SIZE:
                mSearchCriteria.setSize(minMaxReturnObject.getValue());
                String textSize = getMaxMinText(option, minMaxReturnObject);
                mBinding.get().filterSize.setTextValue(textSize);
                break;
            case BEDROOMS:
                mSearchCriteria.setBedrooms(minMaxReturnObject.getValue());
                String textBed = getMaxMinText(option, minMaxReturnObject);
                mBinding.get().filterBedrooms.setTextValue(textBed);
                break;
            case BATHROOMS:
                mSearchCriteria.setBathrooms(minMaxReturnObject.getValue());
                String textBath = getMaxMinText(option, minMaxReturnObject);
                mBinding.get().filterBathRooms.setTextValue(textBath);
                break;
            case PARKING:
                mSearchCriteria.setParking(minMaxReturnObject.getValue());
                String textPark = getMaxMinText(option, minMaxReturnObject);
                mBinding.get().filterParking.setTextValue(textPark);
                break;
            default:
                break;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onCompletePickedType(int requestCode, Object value) {
        FilterOption option = FilterOption.getByRequestCode(requestCode);
        switch (option) {
            case PROPERTY_TYPE:
                List<Integer> listSelectedPropertyType = (List<Integer>) value;
                mSearchCriteria.setPropertyTypeByListInteger(listSelectedPropertyType);
                TextAndMoreTextValue displayValueProperty = TextAndMoreTextValue
                          .build(getContext(), option, mSearchCriteria.getPropertyType());
                mBinding.get().filterPropertyType.setTextValue(displayValueProperty.getText());
                mBinding.get().filterPropertyType.setMoreValue(displayValueProperty.getMoreText());
                break;
            case TAGS:
                List<Tag> selectedTag = TagChip.convertToTag((List<ChipInterface>) value);
                Timber.tag(TAG).d(selectedTag.toString());
                mSearchCriteria.setTags(selectedTag);
                TextAndMoreTextValue displayValueTags = TextAndMoreTextValue
                          .build(getContext(), option, mSearchCriteria.getTags());
                mBinding.get().filterTag.setTextValue(displayValueTags.getText());
                mBinding.get().filterTag.setMoreValue(displayValueTags.getMoreText());
                break;
            default:
                break;
        }
    }
    
    //====================== Contract method =========================//
    //====================== Helper method =========================//
    
    public SearchCriteria getSearchCriteria() {
        return mSearchCriteria;
    }
    
    public void setSearchCriteria(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            this.mSearchCriteria = new SearchCriteria();
            return;
        }
        mSearchCriteria = searchCriteria;
    }
    
    
    private String getMaxMinText(FilterOption option, PickerMinMaxReturnObject object) {
        String any = getString(R.string.txt_any);
        String orLess = getString(R.string.txt_or_less);
        String orMore = getString(R.string.txt_or_more);
        String displayMax = object.getDisplay().getMax();
        String displayMin = object.getDisplay().getMin();
        
        if (option != FilterOption.PRICE) {
            if (displayMax.equals(any) && displayMin.equals(any)) {
                return any;
            }
            
            if (displayMax.equals(displayMin)) {
                return displayMin;
            }
            
            if (option == FilterOption.PARKING) {
                return object.getDisplay().getMin();
            }
            
            if (displayMax.equals(any)) {
                return TextUtils.build(displayMin, " ", orMore);
            }
            if (displayMin.equals(any)) {
                return TextUtils.build(displayMax, " ", orLess);
            }
            
            return object.displayToString();
        } else {
            long valueMin = Long.valueOf(object.getValue().getMin());
            long valueMax = Long.valueOf(object.getValue().getMax());
            
            if (valueMax == valueMin && valueMin == 0) {
                return any;
            }
            if (valueMax == valueMin) {
                return TextUtils.formatIntToCurrency(valueMax * 1000);
            }
            
            if (valueMax == 0) {
                return TextUtils
                          .build(TextUtils.formatIntToCurrency(valueMin * 1000), " ", orMore);
            }
            if (valueMin == 0) {
                return TextUtils
                          .build(TextUtils.formatIntToCurrency(valueMax * 1000), " ", orLess);
            }
            
            return TextUtils.build(TextUtils.formatIntToCurrency(valueMin * 1000), "-",
                      TextUtils.formatIntToCurrency(valueMax * 1000));
        }
    }
}
