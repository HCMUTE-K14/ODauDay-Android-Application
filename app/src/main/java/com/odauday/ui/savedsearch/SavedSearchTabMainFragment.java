package com.odauday.ui.savedsearch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SavedSearchResponse;
import com.odauday.databinding.FragmentSavedSearchTabMainBinding;
import com.odauday.model.Search;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.SnackBarUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class SavedSearchTabMainFragment extends
                                        BaseMVVMFragment<FragmentSavedSearchTabMainBinding> implements
                                                                                            SavedSearchContract {
    
    public static final String TAG = NavigationTab.SAVED_SEARCH_TAB.getNameTab();
    
    @Inject
    SavedSearchViewModel mSavedSearchViewModel;
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    private SavedSearchAdapter mSavedSearchAdapter;
    private RecentSearchAdapter mRecentSearchAdapter;
    private List<Search> mSearches;
    private List<Search> mRecentSearches;
    private EmptySavedSearchAdapter mEmptySavedSearchAdapter;
    private RecyclerView mRecyclerViewSavedSearch;
    private RecyclerView mRecyclerViewRecentSearch;
    private ProgressDialog mProgressDialog;
    private String mSearchID;
    enum STATUS {
        GET, REMOVE
    }
    private STATUS mSTATUS = STATUS.GET;
    RecentSearchAdapter.OnClickRemoveRecentSearches mOnClickRemoveRecentSearches = search -> {
        
        Timber.tag(TAG).d("Remove: " + search.getName());
        
        if (mRecentSearches != null && mRecentSearches.size() > 0) {
            for (Search searchl : mRecentSearches) {
                if (searchl.getId().trim().equals(search.getId().trim())) {
                    mRecentSearches.remove(searchl);
                    break;
                }
            }
            mPreferencesHelper.putList(PrefKey.RECENT_SEARCH, mRecentSearches);
            mRecentSearchAdapter.setData(mRecentSearches);
        }
        
    };
    SavedSearchAdapter.OnClickRemoveSavedSearches mOnClickRemoveSavedSearches = search -> {
        Timber.tag(TAG).d("Remove: " + search.getName());
        mSTATUS = STATUS.REMOVE;
        mSearchID = search.getId();
        mSavedSearchViewModel.removeSearch(search.getId());
    };
    public static SavedSearchTabMainFragment newInstance() {

        Bundle args = new Bundle();

        SavedSearchTabMainFragment fragment = new SavedSearchTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_saved_search_tab_main;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    
    private void initView() {
        
        mSearches = new ArrayList<>();
        mRecentSearches = new ArrayList<>();
        mBinding.get().setHandler(this);
        
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getActivity().getString(R.string.txt_progress));
        mProgressDialog.show();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
    
    private void getData() {
        mSTATUS = STATUS.GET;
        mBinding.get().relativeSavedSearch.setVisibility(View.VISIBLE);
        mBinding.get().relativeRecentSearch.setVisibility(View.VISIBLE);
        mBinding.get().txtSavedSearch.setVisibility(View.VISIBLE);
        mBinding.get().txtRecentSearch.setVisibility(View.VISIBLE);
        
        mRecentSearches = mPreferencesHelper.getList(PrefKey.RECENT_SEARCH, "");
        // mRecentSearches=null;
        mSavedSearchViewModel.getSearchByUser(mPreferencesHelper.get(PrefKey.USER_ID, ""));
    }
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mSavedSearchViewModel;
    }

    @Override
    protected void processingTaskFromViewModel() {
        mSavedSearchViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        onFailure((Exception) resource.data);
                        loading(false);
                        break;
                    case SUCCESS:
                        onSuccess(resource.data);
                        loading(false);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
        mSavedSearchAdapter = new SavedSearchAdapter(getActivity());
        mRecentSearchAdapter = new RecentSearchAdapter(getActivity());
        mEmptySavedSearchAdapter = new EmptySavedSearchAdapter();
        
        mRecentSearchAdapter.setOnClickRemoveRecentSearches(mOnClickRemoveRecentSearches);
        mSavedSearchAdapter.setOnClickRemoveSavedSearches(mOnClickRemoveSavedSearches);
        
        mRecyclerViewSavedSearch = mBinding.get().recycleViewSavedSearch;
        mRecyclerViewRecentSearch = mBinding.get().recycleViewRecentSearch;
        
        mRecyclerViewRecentSearch.setNestedScrollingEnabled(false);
        mRecyclerViewSavedSearch.setNestedScrollingEnabled(false);
        
        mRecyclerViewSavedSearch.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerViewRecentSearch.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        // mRecyclerView.setAdapter(mSavedSearchAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
        Timber.tag(TAG).d("Loading Search");
    }
    
    @Override
    public void onSuccess(Object object) {
        if (mSTATUS == STATUS.REMOVE) {
            if (mSearches != null && mSearches.size() > 0) {
                for (Search searchl : mSearches) {
                    if (searchl.getId().trim().equals(mSearchID.trim())) {
                        mSearches.remove(searchl);
                        break;
                    }
                }
                mSavedSearchAdapter.setData(mSearches);
            }
            mSTATUS = STATUS.GET;
            SnackBarUtils
                .showSnackBar(mBinding.get().savedSearch, ((MessageResponse) object).getMessage());
            return;
        }
        SavedSearchResponse savedSearchResponse = (SavedSearchResponse) object;
        if (savedSearchResponse != null) {
            List<Search> searches = savedSearchResponse.getSearches();
            if (searches != null && !searches.isEmpty()) {
                mSearches = searches;
               // mPreferencesHelper.putList(PrefKey.RECENT_SEARCH,searches);
                if (mRecyclerViewSavedSearch.getAdapter() instanceof SavedSearchAdapter) {
                
                } else {
                    mRecyclerViewSavedSearch.setAdapter(mSavedSearchAdapter);
                }
                mSavedSearchAdapter.setData(searches);
            } else {
                mBinding.get().relativeSavedSearch.setVisibility(View.GONE);
            }
        } else {
            mBinding.get().relativeSavedSearch.setVisibility(View.GONE);
        }
        if (mRecentSearches != null && !mRecentSearches.isEmpty()) {
            if (mRecyclerViewRecentSearch.getAdapter() instanceof RecentSearchAdapter) {
            
            } else {
                mRecyclerViewRecentSearch.setAdapter(mRecentSearchAdapter);
            }
            mRecentSearchAdapter.setData(mRecentSearches);
        } else {
            if (mBinding.get().relativeSavedSearch.getVisibility() == View.GONE) {
                mBinding.get().txtRecentSearch.setVisibility(View.GONE);
                mRecyclerViewRecentSearch.setAdapter(mEmptySavedSearchAdapter);
            } else {
                mBinding.get().relativeRecentSearch.setVisibility(View.GONE);
            }
        }
        
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).d(ex.getMessage());
        if (mSTATUS == STATUS.REMOVE) {
            String message;
            message = getActivity().getString(R.string.cannot_remove_saved_search);
            SnackBarUtils.showSnackBar(mBinding.get().savedSearch, message);
            mSTATUS = STATUS.GET;
            return;
        }
        if (mRecentSearches != null && !mRecentSearches.isEmpty()) {
            //set recent search
            if (mRecyclerViewRecentSearch.getAdapter() instanceof RecentSearchAdapter) {
            
            } else {
                mRecyclerViewRecentSearch.setAdapter(mRecentSearchAdapter);
            }
            mRecentSearchAdapter.setData(mRecentSearches);
            
            mBinding.get().relativeSavedSearch.setVisibility(View.GONE);
            
        } else {
            mRecyclerViewSavedSearch.setAdapter(mEmptySavedSearchAdapter);
            mBinding.get().relativeRecentSearch.setVisibility(View.GONE);
            mBinding.get().txtSavedSearch.setVisibility(View.GONE);
        }
        String message;
        message = getActivity().getString(R.string.empty_recent_saved_search);
        SnackBarUtils.showSnackBar(mBinding.get().savedSearch, message);
    }
    
    public void onClickRefresh() {
        getData();
    }
}
