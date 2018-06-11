package com.odauday.ui.search.listview;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.databinding.FragmentListViewBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.common.SortPropertyEntryUtils;
import com.odauday.ui.search.common.SortType;
import com.odauday.ui.search.common.event.OnCompleteDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnErrorDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnFavouriteEvent;
import com.odauday.ui.search.common.event.OnShowSortDialogEvent;
import com.odauday.ui.view.StarView.OnClickStarListener;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by infamouSs on 6/1/18.
 */
public class ListViewFragment extends BaseMVVMFragment<FragmentListViewBinding> implements
                                                                                OnClickStarListener<PropertyResultEntry>,
                                                                                OnRefreshListener {
    
    public static final String TAG = ListViewFragment.class.getSimpleName();
    
    @Inject
    SearchPropertyRepository mSearchPropertyRepository;
    
    @Inject
    EventBus mBus;
    
    @Inject
    FavoriteRepository mFavoriteRepository;
    
    private ListViewAdapter mAdapter;
    
    private TextView mErrorHeader;
    private TextView mErrorSubHeader;
    
    private int mOrderPosition = -1 ;
    
    
    public static ListViewFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ListViewFragment fragment = new ListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_view;
    }
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus.register(this);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ListViewAdapter(getContext());
        mAdapter.setOnClickStarListener(this);
        mBinding.get().recyclerView.setHasFixedSize(true);
        mBinding.get().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.get().recyclerView.addItemDecoration(
            new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        
        mBinding.get().recyclerView.setHasFixedSize(true);
        mBinding.get().recyclerView.setAdapter(this.mAdapter);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setSupportsChangeAnimations(false);
        mBinding.get().recyclerView.setItemAnimator(animator);
        
        mBinding.get().swipeContainer.setOnRefreshListener(this);
        
        mErrorHeader = mBinding.get().loadingLayout.findViewById(R.id.header);
        mErrorSubHeader = mBinding.get().loadingLayout.findViewById(R.id.sub_header);
        
        mBinding.get().loadingLayout.setOnClickListener(__ -> {
            search();
        });
    }
    
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatedSearchResult(OnCompleteDownloadPropertyEvent completeDownloadProperty) {
        stopSwipe();
        if (completeDownloadProperty.getResult() != null &&
            !completeDownloadProperty.getResult().isEmpty()) {
            mAdapter.setData(completeDownloadProperty.getResult());
            
            mBinding.get().swipeContainer.setVisibility(View.VISIBLE);
            mBinding.get().loadingLayout.setVisibility(View.GONE);
        } else {
            
            showError("no_result");
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorSearchProperty(OnErrorDownloadPropertyEvent errorDownloadProperty) {
        stopSwipe();
        showError("network_error");
    }
    
    private void showError(String type) {
        //0: no result
        //1: network error
        if (type.equals("no_result")) {
            mBinding.get().swipeContainer.setVisibility(View.GONE);
            
            mErrorHeader.setText(R.string.txt_no_search_results);
            mErrorSubHeader.setText(R.string.txt_empty_search_subtitle);
            
            mBinding.get().loadingLayout.setVisibility(View.VISIBLE);
            return;
        }
        mBinding.get().swipeContainer.setVisibility(View.GONE);
        
        mErrorHeader.setText(R.string.message_error_network_header);
        mErrorSubHeader.setText(R.string.message_error_network_sub_header);
        
        mBinding.get().loadingLayout.setVisibility(View.VISIBLE);
    }
    
    private void stopSwipe() {
        mBinding.get().swipeContainer.setRefreshing(false);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mAdapter = null;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowDialogSort(OnShowSortDialogEvent event) {
        showDialogSort();
    }
    
    public void showDialogSort() {
        AlertDialog.Builder builder = new Builder(this.getActivity());
        builder.setTitle(R.string.txt_sort);
        String[] sortList = getResources().getStringArray(R.array.sort_property);
        
        builder.setSingleChoiceItems(sortList, mOrderPosition, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOrderPosition = which;
                SortType sortType = SortType.getSortType(which);
                List<PropertyResultEntry> data = SortPropertyEntryUtils
                    .sort(mAdapter.getData(), sortType);
                mAdapter.setData(data);
                
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
    @Override
    public void onCheckStar(PropertyResultEntry item) {
        mFavoriteRepository.checkFavorite(item.getId())
            .subscribe();
        
        item.setFavorite(true);
        mBus.post(new OnFavouriteEvent(item));
    }
    
    @Override
    public void onUnCheckStar(PropertyResultEntry item) {
        mFavoriteRepository
            .unCheckFavorite(item.getId())
            .subscribe();
        
        item.setFavorite(false);
        mBus.post(new OnFavouriteEvent(item));
    }
    
    @Override
    public void onRefresh() {
        search();
    }
    
    private void search() {
        SearchRequest searchRequest = mSearchPropertyRepository.getCurrentSearchRequest();
        if (searchRequest != null) {
            mSearchPropertyRepository.search(searchRequest);
        }
    }
}
