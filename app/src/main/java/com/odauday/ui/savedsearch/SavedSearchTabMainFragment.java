package com.odauday.ui.savedsearch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import com.odauday.R;
import com.odauday.data.SavedSearchRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SavedSearchResponse;
import com.odauday.databinding.FragmentSavedSearchTabMainBinding;
import com.odauday.model.Search;
import com.odauday.ui.ClearMemory;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class SavedSearchTabMainFragment extends
                                        BaseMVVMFragment<FragmentSavedSearchTabMainBinding> implements
                                                                                            SavedSearchContract,ClearMemory {
    
    public static final String TAG = NavigationTab.SAVED_SEARCH_TAB.getNameTab();
    
    @Inject
    SavedSearchViewModel mSavedSearchViewModel;
    @Inject
    PreferencesHelper mPreferencesHelper;
    @Inject
    SavedSearchRepository mSavedSearchRepository;
    
    private SavedSearchAdapter mSavedSearchAdapter;
    private RecentSearchAdapter mRecentSearchAdapter;
    private List<Search> mSearches;
    private List<Search> mRecentSearches;
    private EmptySavedSearchAdapter mEmptySavedSearchAdapter;
    private RecyclerView mRecyclerViewSavedSearch;
    private RecyclerView mRecyclerViewRecentSearch;
    private RecyclerView mRecyclerViewEmpty;
    private RelativeLayout mRelativeLayoutSavedSearch;
    private RelativeLayout mRelativeLayoutRecentSearch;
    
    RecentSearchAdapter.OnClickRemoveRecentSearches mOnClickRemoveRecentSearches = search -> {
        
        Timber.tag(TAG).d("Remove: " + search.getName());
        
        if (!ValidationHelper.isEmptyList(mRecentSearches)) {
            for (Search s: mRecentSearches) {
                if (s.getId().trim().equals(search.getId().trim())) {
                    mRecentSearches.remove(s);
                    break;
                }
            }
            mPreferencesHelper.putList(PrefKey.RECENT_SEARCH, mRecentSearches);
            mRecentSearchAdapter.setData(mRecentSearches);
            changeView();
            
        }
        
    };
    SavedSearchAdapter.OnClickRemoveSavedSearches mOnClickRemoveSavedSearches = search -> {
        Timber.tag(TAG).d("Remove: " + search.getName());
        mSavedSearchRepository.removeSearch(search.getId())
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("onSubscribe remove saved search");
            })
            .subscribe(success -> {
                if(!ValidationHelper.isNull(success)){
                    onSuccessRemove(success.getMessage(),search.getId());
                }
            }, error -> {
                String message = getActivity().getString(R.string.cannot_remove_saved_search);
                SnackBarUtils.showSnackBar(mBinding.get().savedSearch, message);
            });
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
        getData();
    }
    
    private void initView() {
        
        mSearches = new ArrayList<>();
        mRecentSearches = new ArrayList<>();
        mBinding.get().setHandler(this);
    
        mSavedSearchAdapter = new SavedSearchAdapter();
        mRecentSearchAdapter = new RecentSearchAdapter();
        mEmptySavedSearchAdapter = new EmptySavedSearchAdapter();
    
        mRecentSearchAdapter.setOnClickRemoveRecentSearches(mOnClickRemoveRecentSearches);
        mSavedSearchAdapter.setOnClickRemoveSavedSearches(mOnClickRemoveSavedSearches);
    
        mRecyclerViewSavedSearch = mBinding.get().recycleViewSavedSearch;
        mRecyclerViewRecentSearch = mBinding.get().recycleViewRecentSearch;
        mRecyclerViewEmpty=mBinding.get().recycleViewEmpty;
        mRelativeLayoutSavedSearch=mBinding.get().relativeSavedSearch;
        mRelativeLayoutRecentSearch=mBinding.get().relativeRecentSearch;
    
        mRecyclerViewRecentSearch.setNestedScrollingEnabled(false);
        mRecyclerViewSavedSearch.setNestedScrollingEnabled(false);
    
        mRecyclerViewSavedSearch.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerViewRecentSearch.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerViewEmpty.setLayoutManager(new GridLayoutManager(getActivity(),1));
    }
    
    private void getData() {
        mRecentSearches = mPreferencesHelper.getList(PrefKey.RECENT_SEARCH, "");
        setRecentSearch();
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
    }
    
    @Override
    public void loading(boolean isLoading) {
        Timber.tag(TAG).d("Loading Search");
    }
    
    @Override
    public void onSuccess(Object object) {
        onSuccessSavedSearch(object);
        changeView();
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).d(ex.getMessage());
        changeView();
    }
    private void onSuccessRemove(Object object,String idSavedSearch){
        if (!ValidationHelper.isEmptyList(mSearches)) {
            for (Search search : mSearches) {
                if (search.getId().trim().equals(idSavedSearch)) {
                    mSearches.remove(search);
                    mSavedSearchAdapter.removeItem(search);
                    break;
                }
            }
            changeView();
        }
        SnackBarUtils
            .showSnackBar(mBinding.get().savedSearch, getString(R.string.txt_remove_saved_search_successfully));
    }
    private void onSuccessSavedSearch(Object object){
        SavedSearchResponse savedSearchResponse = (SavedSearchResponse) object;
        if (!ValidationHelper.isNull(savedSearchResponse)) {
            List<Search> searches = savedSearchResponse.getSearches();
            if (!ValidationHelper.isEmptyList(searches)) {
                if(mSavedSearchAdapter.getData()!=null&&mSavedSearchAdapter.getData().equals(searches)){
                    return;
                }
                mSearches = searches;
                if (!(mRecyclerViewSavedSearch.getAdapter() instanceof SavedSearchAdapter)) {
                    mRecyclerViewSavedSearch.setAdapter(mSavedSearchAdapter);
                }
                mSavedSearchAdapter.setData(searches);
            }
        }
    }
    private void setRecentSearch(){
        if (!ValidationHelper.isEmptyList(mRecentSearches)) {
            if (!(mRecyclerViewRecentSearch.getAdapter() instanceof RecentSearchAdapter)) {
                mRecyclerViewRecentSearch.setAdapter(mRecentSearchAdapter);
            }
            mRecentSearchAdapter.setData(mRecentSearches);
        }
        changeView();
    }
    private void changeView(){
        if(!ValidationHelper.isEmptyList(mSearches)){
            mRelativeLayoutSavedSearch.setVisibility(View.VISIBLE);
            mRecyclerViewEmpty.setVisibility(View.GONE);
        }else {
            mRelativeLayoutSavedSearch.setVisibility(View.GONE);
        }
        
        if(!ValidationHelper.isEmptyList(mRecentSearches)){
            mRelativeLayoutRecentSearch.setVisibility(View.VISIBLE);
            mRecyclerViewEmpty.setVisibility(View.GONE);
        }else {
            mRelativeLayoutRecentSearch.setVisibility(View.GONE);
        }
        
        if((mSearches==null||mSearches.size()<1)&&(mRecentSearches==null||mRecentSearches.size()<1)){
            mRelativeLayoutSavedSearch.setVisibility(View.GONE);
            mRelativeLayoutRecentSearch.setVisibility(View.GONE);
            mRecyclerViewEmpty.setVisibility(View.VISIBLE);
            mRecyclerViewEmpty.setAdapter(mEmptySavedSearchAdapter);
        }
    }
    public void onClickRefresh() {
        getData();
    }
    @Override
    public void onStop() {
        clearMemory();
        super.onStop();
    }
    @Override
    public void clearMemory() {
        mSavedSearchAdapter=null;
        mRecentSearchAdapter=null;
        mSearches=null;
        mRecentSearches=null;
        mEmptySavedSearchAdapter=null;
        mRecyclerViewSavedSearch=null;
        mRecyclerViewRecentSearch=null;
        mRecyclerViewEmpty=null;
        mRelativeLayoutSavedSearch=null;
        mRelativeLayoutRecentSearch=null;
    }
}
