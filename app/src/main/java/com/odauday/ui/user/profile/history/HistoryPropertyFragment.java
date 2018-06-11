package com.odauday.ui.user.profile.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.config.Constants.Task;
import com.odauday.data.remote.history.HistoryDetailResultEntry;
import com.odauday.databinding.FragmentHistoryPropertyBinding;
import com.odauday.model.HistoryDetail;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.user.profile.history.HistoryPropertyAdapter.OnClickHistory;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/29/18.
 */
public class HistoryPropertyFragment extends
                                     BaseMVVMFragment<FragmentHistoryPropertyBinding> implements
                                                                                      HistoryPropertyContract,
                                                                                      SwipeRefreshLayout.OnRefreshListener,
                                                                                      OnClickHistory,
                                                                                      ClearHistoryCallBack {
    
    public static final String TAG = HistoryPropertyFragment.class.getSimpleName();
    
    private HistoryPropertyAdapter mAdapter;
    
    @Inject
    HistoryPropertyViewModel mHistoryPropertyViewModel;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_property;
    }
    
    public static HistoryPropertyFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HistoryPropertyFragment fragment = new HistoryPropertyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        mBinding.get().recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
                
                if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() ==
                              (mAdapter.getItemCount() - 2)) {
                    mHistoryPropertyViewModel.loadNextPage();
                }
            }
        });
        
        mBinding.get().swipe.setOnRefreshListener(this);
        
        mAdapter = new HistoryPropertyAdapter(this);
        
        mBinding.get().recyclerView.setAdapter(mAdapter);
        
        mHistoryPropertyViewModel.getHistory();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mHistoryPropertyViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mHistoryPropertyViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(Task.TASK_GET_HISTORY)) {
                            loading(false);
                            onFailure((Exception) resource.data);
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(Task.TASK_GET_HISTORY)) {
                            loading(false);
                            onSuccess(resource.data);
                        }
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
        mBinding.get().progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onSuccess(Object object) {
        stopRefreshSwipe();
        mBinding.get().infoContainer.setVisibility(View.GONE);
        
        HistoryDetailResultEntry result = (HistoryDetailResultEntry) object;
        if (mAdapter.getItemCount() == result.getHistories().size()) {
            return;
        }
        if (result.getHistories().isEmpty()) {
            showMessage(R.string.message_error_no_history);
        } else {
            mAdapter.setData(result.getHistories());
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        stopRefreshSwipe();
        if (mAdapter.getItemCount() > 0) {
            return;
        }
        showMessage(R.string.message_error_load_history);
    }
    
    private void showMessage(int resourceString) {
        mBinding.get().infoContainer.setVisibility(View.VISIBLE);
        mBinding.get().errorMessage.setText(resourceString);
    }
    
    @Override
    public void onRefresh() {
        mHistoryPropertyViewModel.resetStage();
        mAdapter.reset();
        mHistoryPropertyViewModel.getHistory();
    }
    
    private void stopRefreshSwipe() {
        mBinding.get().swipe.setRefreshing(false);
    }
    
    @Override
    public void onClickHistory(HistoryDetail item) {
        PropertyDetail propertyDetail = new PropertyDetail(item);
        Intent intent = new Intent(getContext(), PropertyDetailActivity.class);
        
        intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, propertyDetail);
        
        startActivity(intent);
    }
    
    @Override
    public void onSuccessClearHistory() {
        mAdapter.reset();
        showMessage(R.string.message_error_no_history);
    }
    
    @Override
    public void onFailureClearHistory() {
        mAdapter.reset();
        showMessage(R.string.message_error_load_history);
    }
}
