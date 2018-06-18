package com.odauday.ui.admin.usermanager.userdetail;

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
import com.odauday.data.remote.history.HistoryDetailResultEntry;
import com.odauday.databinding.FragmentHistoryPropertyBinding;
import com.odauday.model.HistoryDetail;
import com.odauday.model.PropertyDetail;
import com.odauday.model.User;
import com.odauday.ui.base.BaseContract;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.user.profile.history.HistoryPropertyAdapter;
import com.odauday.ui.user.profile.history.HistoryPropertyAdapter.OnClickHistory;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 6/11/2018.
 */

public class FragmentUserHistory extends BaseMVVMFragment<FragmentHistoryPropertyBinding> implements
    BaseContract, SwipeRefreshLayout.OnRefreshListener,OnClickHistory {
    @Inject
    UserHistoryModel mUserHistoryModel;
    private static final String TAG=FragmentUserHistory.class.getSimpleName();
    private HistoryPropertyAdapter mHistoryPropertyAdapter;
    private User mUser;
    private HistoryPropertyAdapter.OnClickHistory mOnClickHistory=item -> {
        Timber.tag(TAG).d(item.getAddress());
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_property;
    }
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    public static FragmentUserHistory newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentUserHistory fragment = new FragmentUserHistory();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUser=((ActivityUserDetail)getActivity()).getUser();
        init();
    }
    
    private void init() {
        mBinding.get().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    
        mBinding.get().recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
            
                if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() ==
                              (mHistoryPropertyAdapter.getItemCount() - 2)) {
                    if(mUser!=null){
                        mUserHistoryModel.loadNextPage(mUser.getId());
                    }
                    
                }
            }
        });
    
        mBinding.get().swipe.setOnRefreshListener(this);
    
        mHistoryPropertyAdapter = new HistoryPropertyAdapter(mOnClickHistory);
        mHistoryPropertyAdapter.setOnClickHistory(this);
        mBinding.get().recyclerView.setAdapter(mHistoryPropertyAdapter);
    
        if(mUser!=null){
            mUserHistoryModel.getHistoryByUser(mUser.getId());
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    protected void processingTaskFromViewModel() {
        mUserHistoryModel.response().observe(this, resource -> {
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
        mBinding.get().progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onSuccess(Object object) {
        stopRefreshSwipe();
        mBinding.get().infoContainer.setVisibility(View.GONE);
    
        HistoryDetailResultEntry result = (HistoryDetailResultEntry) object;
        if (mHistoryPropertyAdapter.getItemCount() == result.getHistories().size()) {
            return;
        }
        if (result.getHistories().isEmpty()) {
            showMessage(R.string.message_error_no_history);
        } else {
            mHistoryPropertyAdapter.setData(result.getHistories());
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        stopRefreshSwipe();
        if (mHistoryPropertyAdapter.getItemCount() > 0) {
            return;
        }
        showMessage(R.string.message_error_load_history);
    }
    
    @Override
    public void onRefresh() {
        mUserHistoryModel.resetStage();
        mHistoryPropertyAdapter.reset();
        if(mUser!=null){
            mUserHistoryModel.getHistoryByUser(mUser.getId());
        }
    }
    private void stopRefreshSwipe() {
        mBinding.get().swipe.setRefreshing(false);
    }
    private void showMessage(int resourceString) {
        mBinding.get().infoContainer.setVisibility(View.VISIBLE);
        mBinding.get().errorMessage.setText(resourceString);
    }
    
    @Override
    public void onClickHistory(HistoryDetail item) {
        if(item!=null){
            PropertyDetail propertyDetail = new PropertyDetail(item);
            Intent intent = new Intent(getContext(), PropertyDetailActivity.class);
    
            intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, propertyDetail);
    
            startActivity(intent);
        }
    }
}
