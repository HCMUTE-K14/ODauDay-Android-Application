package com.odauday.ui.user.subscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.config.Constants.Task;
import com.odauday.data.remote.premium.model.SubscribeRequest;
import com.odauday.databinding.ActivitySubscribePremiumBinding;
import com.odauday.model.Premium;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.buypoint.BuyPointViewModel;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/31/18.
 */
public class SubscribePremiumActivity extends
                                      BaseMVVMActivity<ActivitySubscribePremiumBinding> implements
                                                                                        SubscribePremiumContract {
    
    @Inject
    BuyPointViewModel mBuyPointViewModel;
    
    private Premium mPremium;
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mBuyPointViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mBuyPointViewModel.response().observe(this, resource -> {
            if (resource != null) {
                String task = resource.task;
                switch (resource.status) {
                    case ERROR:
                        if (task.equals(Task.TASK_SUBSCRIBE)) {
                            hideProgress();
                            onFailureSubscribe();
                        }
                        break;
                    case SUCCESS:
                        if (task.equals(Task.TASK_SUBSCRIBE)) {
                            hideProgress();
                            onSuccessSubscribe();
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
        return R.layout.activity_subscribe_premium;
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getIntent() != null) {
            mPremium = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_PREMIUM);
        }
        if (mPremium == null) {
            finish();
            return;
        }
        
        initToolBar();
        initListener();
        
    }
    
    private void initListener() {
        mBinding.subscribe.setOnClickListener(view -> {
            subscribe();
        });
        mBinding.btnOk.setOnClickListener(view -> {
            finish();
        });
    }
    
    private void subscribe() {
        String serial = mBinding.serial.getText().toString();
        String pin = mBinding.serial.getText().toString();
        
        boolean isValid = validate(serial, pin);
        if (isValid) {
            SubscribeRequest request = new SubscribeRequest();
            request.setPinNumber(pin);
            request.setSerialNumber(serial);
            request.setPremiumId(mPremium.getId());
            
            mBuyPointViewModel.subscribe(request);
        }
    }
    
    private void initToolBar() {
        setSupportActionBar(mBinding.toolbar);
        
        if (getSupportActionBar() != null) {
            String title = getString(R.string.txt_tittle_subscribe_plan, mPremium.getName());
            
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    private boolean validate(String serial, String pin) {
        mBinding.txtInputPin.setErrorEnabled(false);
        mBinding.txtInputSerial.setErrorEnabled(false);
        
        if (ValidationHelper.isEmpty(serial)) {
            mBinding.txtInputSerial.setError(getString(R.string.message_serial_number_is_required));
            mBinding.serial.requestFocus();
            return false;
        }
        
        if (ValidationHelper.isEmpty(pin)) {
            mBinding.txtInputPin.setError(getString(R.string.message_pin_number_is_required));
            mBinding.pin.requestFocus();
            return false;
        }
        
        return true;
    }
    
    @Override
    public void onSuccessSubscribe() {
        mBinding.inputContainer.setVisibility(View.GONE);
        mBinding.successContainer.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void onFailureSubscribe() {
        SnackBarUtils.showSnackBar(findViewById(android.R.id.content),
            R.string.message_some_thing_went_wrong_when_subscribing);
    }
    
    @Override
    public void showProgress() {
        ViewUtils.disabledUserInteraction(this);
        mBinding.progress.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgress() {
        ViewUtils.enabledUserInteraction(this);
        mBinding.progress.setVisibility(View.GONE);
    }
}
