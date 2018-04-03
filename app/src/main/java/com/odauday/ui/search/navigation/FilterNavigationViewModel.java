package com.odauday.ui.search.navigation;

import com.odauday.R;
import com.odauday.ui.search.common.view.FilterNumberPickerDialog;
import com.odauday.ui.search.common.view.propertydialog.PropertyTypeDialog;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class FilterNavigationViewModel extends BaseViewModel {

    private FilterNavigationFragment mFragment;

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
        switch (option) {
            case PROPERTY_TYPE:
                //GET SELECTED PROPERTY TYPE
                PropertyTypeDialog dialog = PropertyTypeDialog
                    .newInstance(new ArrayList<>());
                if (dialog == null) {
                    return;
                }
                dialog.setTargetFragment(mFragment, option.getRequestCode());
                dialog.show(mFragment.getFragmentManager(), option.getTag());
                break;
            default:
                break;
        }
    }

    public void showNumberPickerDialog(FilterOption option) {
        FilterNumberPickerDialog pd;
        String title;

        switch (option) {
            case PRICE:
                title = mFragment.getString(R.string.txt_filter_price);
                //SERACH TYPE ==> RENT OR BUY
                pd = initNumberPickerDialog(true, title, R.array.price_buy,
                    0, 0, 0, true);
                break;
            case SIZE:
                title = mFragment.getString(R.string.txt_filter_size);
                pd = initNumberPickerDialog(false, title, R.array.land_size_count_int,
                    R.array.land_size_count_string, 0, 0, true);
                break;
            case BEDROOMS:
                title = mFragment.getString(R.string.txt_filter_num_of_bedroom);
                pd = initNumberPickerDialog(false, title, R.array.bedroom_count_int,
                    R.array.bedroom_count_string, 0, 0, true);
                break;
            case BATHROOMS:
                title = mFragment.getString(R.string.txt_filter_num_of_bathroom);
                pd = initNumberPickerDialog(false, title, R.array.bathroom_count_int,
                    R.array.bathroom_count_string, 0, 0, true);
                break;
            case PARKING:
                title = mFragment.getString(R.string.txt_filter_num_of_parking);
                pd = initNumberPickerDialog(false, title, R.array.parking_count_int,
                    R.array.parking_count_string, 0, 0, false);
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
}
