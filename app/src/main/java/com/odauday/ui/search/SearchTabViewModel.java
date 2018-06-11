package com.odauday.ui.search;

import static com.odauday.config.Constants.Task.TASK_CREATE_SAVED_SEARCH;

import android.view.View;
import android.view.animation.AnimationUtils;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.SavedSearchRepository;
import com.odauday.model.Search;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/31/18.
 */

public class SearchTabViewModel extends BaseViewModel {
    
    private SearchTabMainFragment mSearchTabMainFragment;
    
    private final SavedSearchRepository mSavedSearchRepository;
    
    @Inject
    public SearchTabViewModel(SavedSearchRepository savedSearchRepository) {
        this.mSavedSearchRepository = savedSearchRepository;
    }
    
    
    public void showVitalProperty(boolean show) {
        if (mSearchTabMainFragment.getActivity() != null) {
            View view = getVitalPropertyContainer();
            startAnimation(view, show);
        }
    }
    
    public void showBottomBar(boolean show) {
        if (mSearchTabMainFragment.getActivity() != null) {
            View view = getBottomBar();
            startAnimation(view, show);
        }
    }
    
    private void startAnimation(View view, boolean show) {
        if (view == null) {
            return;
        }
        if (show) {
            view.startAnimation(
                AnimationUtils.loadAnimation(this.mSearchTabMainFragment.getContext(),
                    R.anim.slide_up));
            view.setVisibility(View.VISIBLE);
        } else {
            view.startAnimation(
                AnimationUtils.loadAnimation(this.mSearchTabMainFragment.getContext(),
                    R.anim.slide_down));
            view.setVisibility(View.GONE);
        }
    }
    
    public View getVitalPropertyContainer() {
        if (mSearchTabMainFragment.getActivity() != null) {
            return ((MainActivity) mSearchTabMainFragment.getActivity())
                .getBinding().vitalPropertyContainer;
        }
        return null;
    }
    
    public View getBottomBar() {
        if (mSearchTabMainFragment.getActivity() != null) {
            return ((MainActivity) mSearchTabMainFragment.getActivity()).getBinding().bottomNavBar;
        }
        return null;
    }
    
    public void setSearchTabMainFragment(SearchTabMainFragment searchTabMainFragment) {
        mSearchTabMainFragment = searchTabMainFragment;
    }
    
    public void createSearch(Search search) {
        Disposable disposable = mSavedSearchRepository
            .saveSearch(search)
            .subscribe(success -> {
                response.setValue(Resource.success(TASK_CREATE_SAVED_SEARCH, success));
            }, throwable -> {
                response.setValue(Resource.error(TASK_CREATE_SAVED_SEARCH, throwable));
            });
        mCompositeDisposable.add(disposable);
    }
}
