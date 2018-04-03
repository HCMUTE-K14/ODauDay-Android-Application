package com.odauday.ui.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.GithubUserActivityBinding;
import com.odauday.exception.BaseException;
import com.odauday.model.User;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/28/18.
 */

public class GithubUserActivity extends
    BaseMVVMActivity<GithubUserActivityBinding> implements
    GithubUserContract {

    @Inject
    GithubUserViewModel mGithubUserViewModel;

    private GithubUserAdapter mGithubUserAdapter;

    @Override
    public void showData(List<User> users) {
        mGithubUserAdapter.setData(users);
    }

    @Override
    public void handlerError(Exception ex) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void processingTaskFromViewModel() {

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onStart() {
        super.onStart();
        mGithubUserViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        showOrHideProgressBar(false);
                        break;
                    case SUCCESS:
                        showOrHideProgressBar(false);
                        showData((List<User>) resource.data);
                        break;
                    case LOADING:
                        showOrHideProgressBar(true);
                        break;
                    default:
                        break;
                }
            } else {
                handlerError(new BaseException("Unknown error"));
            }
        });
        mGithubUserAdapter = new GithubUserAdapter();

        mBinding.repoList.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
                int lastPosition = layoutManager
                    .findLastVisibleItemPosition();
                if (lastPosition == mGithubUserAdapter.getItemCount() - 1) {
                    mGithubUserViewModel.loadNextPage();
                }
            }
        });

        mBinding.repoList.setAdapter(mGithubUserAdapter);
        mGithubUserViewModel.getData();
    }

    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mGithubUserViewModel;
    }

    private void showOrHideProgressBar(boolean isLoading) {
        new Handler().postDelayed(
            () -> mBinding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE),
            1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.github_user_activity;
    }
}
