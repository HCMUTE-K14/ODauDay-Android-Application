package com.odauday.ui.search.autocomplete;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.databinding.ActivityAutoCompletePlaceBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceAdapter.OnClickItemSearchPlace;
import com.odauday.ui.search.autocomplete.RxAutoCompleteSearchBox.OnSearchQuery;
import com.odauday.ui.search.common.event.OnSelectedPlaceEvent;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by infamouSs on 4/22/18.
 */
public class AutoCompletePlaceActivity extends
                                       BaseMVVMActivity<ActivityAutoCompletePlaceBinding> implements
                                                                                          OnSearchQuery,
                                                                                          OnClickItemSearchPlace,
                                                                                          AutoCompletePlaceContract {
    
    
    public static final String TAG = AutoCompletePlaceActivity.class.getSimpleName();
    
    public static final int REQUEST_CODE = 101;
    
    @Inject
    AutoCompletePlaceViewModel mAutoCompletePlaceViewModel;
    
    @Inject
    EventBus mBus;
    
    private RxAutoCompleteSearchBox mRxAutoCompleteSearchBox;
    
    private AutoCompletePlaceAdapter mCompletePlaceAdapter;
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mAutoCompletePlaceViewModel;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoCompletePlaceViewModel.setContract(this);
        mBinding.txtSearch.getBackground().clearColorFilter();
        
        mRxAutoCompleteSearchBox = new RxAutoCompleteSearchBox(mBinding.txtSearch, this);
        
        mCompletePlaceAdapter = new AutoCompletePlaceAdapter(this);
        mBinding.listItem.setAdapter(mCompletePlaceAdapter);
        mCompletePlaceAdapter.showEmptyStateCards();
    }
    
    @Override
    protected void onStart() {
        mRxAutoCompleteSearchBox.start();
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        mRxAutoCompleteSearchBox.stop();
        super.onStop();
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mAutoCompletePlaceViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(resource.data);
                        break;
                    case LOADING:
                        loading(true);
                        mCompletePlaceAdapter.hideRecentSearchCard();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_complete_place;
    }
    
    @Override
    protected void onDestroy() {
        mAutoCompletePlaceViewModel = null;
        mRxAutoCompleteSearchBox = null;
        super.onDestroy();
    }
    
    @Override
    public void finish() {
        overridePendingTransition(0, android.R.anim.fade_out);
        super.finish();
    }
    
    public void back(View view) {
        finish();
    }
    
    public void clearTextSearch(View view) {
        mBinding.txtSearch.setText("");
    }
    
    @Override
    public void onSearchQuery(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            mAutoCompletePlaceViewModel.searchLocal();
            hideClearButton();
        } else if (keyword.length() > 1) {
            showClearButton();
            mAutoCompletePlaceViewModel.search(keyword);
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedPlace(OnSelectedPlaceEvent onSelectedPlaceEvent) {
        finish();
    }
    
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    @Override
    public void onSuccess(Object object) {
        hideSoftKeyboard();
        AutoCompletePlaceCollection collection = ((AutoCompletePlaceCollection) object);
        mCompletePlaceAdapter.setData(collection);
    }
    
    public void hideSoftKeyboard() {
        ViewUtils.hideSoftKeyboard(this, mBinding.txtSearch.getWindowToken());
    }
    
    @Override
    public void onFailure(Exception ex) {
        SnackBarUtils.showSnackBar(mBinding.getRoot(),
                  getString(R.string.message_some_thing_went_wrong_when_find_place));
        mAutoCompletePlaceViewModel.searchLocal();
    }
    
    @Override
    public void onSuccessGetRecentSearchFromLocal(List<AutoCompletePlace> autoCompletePlaces) {
        mCompletePlaceAdapter.setRecentSearches(autoCompletePlaces);
        mCompletePlaceAdapter.showEmptyStateCards();
    }
    
    @Override
    public void onSuccessDeleteRecentSearchFromLocal(AutoCompletePlace autoCompletePlace) {
        mCompletePlaceAdapter.removeItemRecentSearch(autoCompletePlace);
    }
    
    @Override
    public void hideClearButton() {
        ViewUtils.showHideView(mBinding.txtClear, false);
    }
    
    @Override
    public void showClearButton() {
        ViewUtils.showHideView(mBinding.txtClear, true);
    }
    
    @Override
    public void onSelectedSuggestionPlace(AutoCompletePlace autoCompletePlace) {
        mAutoCompletePlaceViewModel.create(autoCompletePlace);
        finish();
        mBus.post(new OnSelectedPlaceEvent(autoCompletePlace));
    }
    
    @Override
    public void onRemoveRecentSearchPlace(AutoCompletePlace autoCompletePlace) {
        AlertDialog dialog = createDialogConfirmDeleteRecentSearch(autoCompletePlace);
        dialog.show();
    }
    
    private AlertDialog createDialogConfirmDeleteRecentSearch(AutoCompletePlace autoCompletePlace) {
        return new AlertDialog.Builder(this)
                  .setTitle(R.string.txt_wait_a_second)
                  .setMessage(R.string.message_are_u_sure_to_delete_this_item)
                  .setNegativeButton(R.string.txt_cancel, (dialog12, which) -> {
                      dialog12.cancel();
                      dialog12.dismiss();
                  })
                  .setPositiveButton(R.string.txt_ok,
                            (dialog1, which) -> mAutoCompletePlaceViewModel
                                      .delete(autoCompletePlace))
                  .create();
    }
}
