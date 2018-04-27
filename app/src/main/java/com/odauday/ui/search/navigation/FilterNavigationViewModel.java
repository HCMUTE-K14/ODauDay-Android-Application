package com.odauday.ui.search.navigation;

import static com.odauday.config.AppConfig.RATE_VND;

import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants.Task;
import com.odauday.data.RecentTagRepository;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.SearchTabMainFragment;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.common.MinMaxObject;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.SearchType;
import com.odauday.ui.search.common.view.FilterNumberPickerDialog;
import com.odauday.ui.search.common.view.PickerMinMaxReturnObject;
import com.odauday.ui.search.common.view.propertydialog.PropertyTypeDialog;
import com.odauday.ui.search.common.view.tagdialog.TagTypeDialog;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class FilterNavigationViewModel extends BaseViewModel {
    
    public static final String TAG = FilterNavigationViewModel.class.getSimpleName();
    
    
    private final RecentTagRepository mRecentTagRepository;
    
    private FilterNavigationFragment mFragment;
    
    private boolean mIsShowMoreOptions = false;
    
    
    @Inject
    public FilterNavigationViewModel(RecentTagRepository recentTagRepository) {
        this.mRecentTagRepository = recentTagRepository;
    }
    
    public void insertCurrentTags(List<Tag> tags) {
        Disposable disposable = mRecentTagRepository
                  .save(tags)
                  .subscribe(success -> response
                                      .setValue(Resource.success(Task.TASK_CREATE_TAGS, success)),
                            error -> response.setValue(Resource.error(Task.TASK_CREATE_TAGS, error)));
        
        mCompositeDisposable.add(disposable);
    }
    
    public FilterNavigationFragment getFragment() {
        return mFragment;
    }
    
    public void setFragment(FilterNavigationFragment fragment) {
        mFragment = fragment;
    }
    
    public void showAutoCompleteSearchPlace() {
        ViewUtils.startActivity(mFragment.getActivity(), AutoCompletePlaceActivity.class);
        if (mFragment.getParentFragment() != null) {
            ((SearchTabMainFragment) mFragment.getParentFragment()).closeDrawer();
        }
    }
    
    public void showTypePickerDialog(FilterOption option) {
        switch (option) {
            case PROPERTY_TYPE:
                List<Integer> selectedPropertyType =
                          PropertyType.convertToArrayInt(mFragment.getSearchCriteria()
                                    .getPropertyType());
                openPropertyTypeDialog(selectedPropertyType);
                break;
            case TAGS:
                final List<Tag> recentTags = new ArrayList<>();
                
                mRecentTagRepository
                          .findAllRecentTagByCurrentUserId()
                          .subscribe(success -> {
                              recentTags.addAll(success);
                              List<Tag> selectedTag = mFragment.getSearchCriteria().getTags();
                              openTagTypeDialog(selectedTag, recentTags);
                          }, error -> {
                              List<Tag> selectedTag = mFragment.getSearchCriteria().getTags();
                              openTagTypeDialog(selectedTag, recentTags);
                          });
                break;
            default:
                break;
        }
    }
    
    private void openTagTypeDialog(List<Tag> selectedTag, List<Tag> recentTag) {
        if (mFragment.getFragmentManager() == null) {
            return;
        }
        BaseDialogFragment dialog = TagTypeDialog
                  .newInstance(selectedTag, recentTag);
        
        if (dialog == null) {
            return;
        }
        
        dialog.setTargetFragment(mFragment, FilterOption.TAGS.getRequestCode());
        dialog.show(mFragment.getFragmentManager(), FilterOption.TAGS.getTag());
    }
    
    private void openPropertyTypeDialog(List<Integer> selectedPropertyType) {
        if (mFragment.getFragmentManager() == null) {
            return;
        }
        BaseDialogFragment dialog = PropertyTypeDialog
                  .newInstance(selectedPropertyType);
        
        if (dialog == null) {
            return;
        }
        
        dialog.setTargetFragment(mFragment, FilterOption.PROPERTY_TYPE.getRequestCode());
        dialog.show(mFragment.getFragmentManager(), FilterOption.PROPERTY_TYPE.getTag());
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
                
                switch (searchType) {
                    case BUY:
                        pd = initNumberPickerDialog(true, title, R.array.price_buy,
                                  0, priceFromTo.getMin(), priceFromTo.getMax(), true);
                        break;
                    case RENT:
                        pd = initNumberPickerDialog(true, title, R.array.price_rent,
                                  0, priceFromTo.getMin(), priceFromTo.getMax(), true);
                        break;
                    default:
                        pd = null;
                        break;
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
                MinMaxObject<Integer> parkings = mFragment.getSearchCriteria().getParking();
                
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
        resetViewWhenChangeSearchType(searchType);
        
        if (mFragment.isFirstTime()) {
            mFragment.setFirstTime(false);
            return;
        }
        
        mFragment.getMapPreferenceHelper().putLastSearchMode(searchType.getValue());
        mFragment.setSearchCriteria(mFragment.getMapPreferenceHelper()
                  .getRecentSearchCriteria(searchType.getValue()));
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
        
        mFragment.getBinding().get().filterAdvanceOption
                  .postDelayed(() -> mFragment.getBinding().get().scrollView
                            .fullScroll(View.FOCUS_DOWN), 500);
    }
    
    private void showAdvancedOptions(boolean show) {
        mFragment.getBinding().get().filterAdvanceOption
                  .setVisibility(show ? View.VISIBLE : View.GONE);
        mFragment.getBinding().get().btnMoreOptions.setText(show ? R.string.txt_filter_less_options
                  : R.string.txt_filter_more_options);
        
    }
    
    public void resetFilter(View view) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSearchType(mFragment.getSearchCriteria().getSearchType());
        mFragment.setSearchCriteria(searchCriteria);
    }
    
    public void resetMoreOptions() {
        mIsShowMoreOptions = false;
        mFragment.getBinding().get().btnMoreOptions.setText(R.string.txt_filter_more_options);
        mFragment.getBinding().get().filterAdvanceOption.setVisibility(View.GONE);
    }
    
    
    public void completeRefineFilter(View view) {
        if (mFragment.getOnCompleteRefineFilter() != null) {
            mFragment.getOnCompleteRefineFilter()
                      .onCompleteRefineFilter(mFragment.getSearchCriteria());
        }
    }
    
    public String getMaxMinText(FilterOption option, PickerMinMaxReturnObject object) {
        String any = mFragment.getString(R.string.txt_any);
        String orLess = mFragment.getString(R.string.txt_or_less);
        String orMore = mFragment.getString(R.string.txt_or_more);
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
                return TextUtils.formatIntToCurrency(valueMax * RATE_VND);
            }
            
            if (valueMax == 0) {
                return TextUtils
                          .build(TextUtils.formatIntToCurrency(valueMin * RATE_VND), " ", orMore);
            }
            if (valueMin == 0) {
                return TextUtils
                          .build(TextUtils.formatIntToCurrency(valueMax * RATE_VND), " ", orLess);
            }
            
            return TextUtils.build(TextUtils.formatIntToCurrency(valueMin * RATE_VND), "-",
                      TextUtils.formatIntToCurrency(valueMax * RATE_VND));
        }
    }
}
