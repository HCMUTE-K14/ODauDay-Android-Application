package com.odauday.ui.user.buypoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants.Task;
import com.odauday.databinding.ActivityBuyPointBinding;
import com.odauday.model.Premium;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.buypoint.common.SubscriptionPackView;
import com.odauday.ui.view.TextViewWithLeftIconView;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/31/18.
 */
public class BuyPointActivity extends BaseMVVMActivity<ActivityBuyPointBinding> implements
                                                                                BuyPointContract {
    
    @Inject
    BuyPointViewModel mBuyPointViewModel;
    
    private boolean mIsNeedLoadPremiums = true;
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mBuyPointViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mBuyPointViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(Task.TASK_GET_PREMIUM)) {
                            mIsNeedLoadPremiums = true;
                            hideProgress();
                            onFailureGetPremium();
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(Task.TASK_GET_PREMIUM) && mIsNeedLoadPremiums) {
                            mIsNeedLoadPremiums = false;
                            hideProgress();
                            onSuccessGetPremium((List<Premium>) resource.data);
                        }
                        break;
                    case LOADING:
                        showProgress();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_point;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initDescriptionPoint();
        mBuyPointViewModel.getPremium();
        
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    
    private void initToolBar() {
        setSupportActionBar(mBinding.toolbar);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.txt_buy_point);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void initDescriptionPoint() {
        List<BuyPointDescription> buyPointDescriptions = new ArrayList<>();
        
        buyPointDescriptions.add(BuyPointDescription.ACCESS_TO_ALL_PROPERTY);
        buyPointDescriptions.add(BuyPointDescription.USING_POINT_TO_CREATE_NEW_PROPERTY);
        buyPointDescriptions.add(BuyPointDescription.USING_POINT_TO_EXTEND_EXPIRATION_PROPERTY);
        
        for (BuyPointDescription i : buyPointDescriptions) {
            TextViewWithLeftIconView view = new TextViewWithLeftIconView(this);
            
            view.getText().setText(i.getText());
            
            mBinding.pointDescriptionContainer.addView(view);
        }
    }
    
    @Override
    public void onSuccessGetPremium(List<Premium> premiums) {
        
        for (int i = premiums.size() - 1; i >= 0; i--) {
            Premium premium = premiums.get(i);
            SubscriptionPackView view = new SubscriptionPackView(this);
            view.setPremium(premium);
            mBinding.subscriptionContainer.addView(view);
        }
    }
    
    @Override
    public void onFailureGetPremium() {
    }
    
    @Override
    public void showProgress() {
        mBinding.progress.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgress() {
        mBinding.progress.setVisibility(View.GONE);
    }
}
