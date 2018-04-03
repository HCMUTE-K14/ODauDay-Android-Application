package com.odauday.ui.search.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.databinding.FragmentFilterBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.common.view.FilterNumberPickerDialog.OnCompletePickedListener;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/1/18.
 */

public class FilterNavigationFragment extends BaseMVVMFragment<FragmentFilterBinding> implements
    OnCompletePickedListener, OnCompletePickedType {

    //====================== Variable =========================//
    public static final String TAG = FilterNavigationFragment.class.getSimpleName();

    @Inject
    FilterNavigationViewModel mFilterNavigationViewModel;

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
        mFilterNavigationViewModel.setFragment(this);
        mBinding.get().setViewModel(mFilterNavigationViewModel);
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
    public void onCompletePickedNumber(int requestCode, int pickedValueFrom, int pickedValueTo) {

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

    @SuppressWarnings("unchecked")
    @Override
    public void onCompletePickedType(int requestCode, Object value) {
        FilterOption option = FilterOption.getByRequestCode(requestCode);
        switch (option) {
            case PROPERTY_TYPE:
                List<Integer> listSelectedPropertyType = (List<Integer>) value;
                Timber.d(listSelectedPropertyType.toString());
                break;
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

        mBinding.get().btnMoreOptions.setOnClickListener(view -> {
            toggleMoreOptions();
        });
    }

    public void toggleMoreOptions() {
        mIsShowMoreOptions = !mIsShowMoreOptions;
        showAdvancedOptions(mIsShowMoreOptions);
        mBinding.get().btnMoreOptions.postDelayed(() -> {
            mBinding.get().scrollView
                .fullScroll(View.FOCUS_DOWN);
        }, 500);
    }

    private void showAdvancedOptions(boolean show) {
        mBinding.get().filterAdvanceOption.setVisibility(show ? View.VISIBLE : View.GONE);
        mBinding.get().btnMoreOptions.setText(show ? R.string.txt_filter_less_options
            : R.string.txt_filter_more_options);
    }

    private void resetMoreOptions() {
        mIsShowMoreOptions = false;
        mBinding.get().btnMoreOptions.setText(R.string.txt_filter_more_options);
        mBinding.get().filterAdvanceOption.setVisibility(View.GONE);
    }

}
