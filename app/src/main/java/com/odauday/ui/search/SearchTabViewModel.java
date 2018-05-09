package com.odauday.ui.search;

import android.view.View;
import android.view.animation.AnimationUtils;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.UserRepository;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/31/18.
 */

public class SearchTabViewModel extends BaseViewModel {
    
    private SearchTabMainFragment mSearchTabMainFragment;
    
    @Inject
    public SearchTabViewModel(UserRepository userRepository) {
    
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
}
