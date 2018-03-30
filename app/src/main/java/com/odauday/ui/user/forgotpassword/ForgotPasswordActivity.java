package com.odauday.ui.user.forgotpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.users.ForgotPasswordRequest;
import com.odauday.databinding.ActivityForgotPasswordBinding;
import com.odauday.exception.RetrofitException;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.view.MyProgressBar.MyProgressBarListener;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/29/18.
 */

public class ForgotPasswordActivity extends
                                    BaseMVVMActivity<ActivityForgotPasswordBinding> implements
                                                                                    ForgotPasswordContract {
    
    //====================== Variable ==================================//
    public static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    
    private final MyProgressBarListener mProgressBarListener = new MyProgressBarListener() {
        @Override
        public void onShow() {
            ViewUtils.disabledUserInteraction(ForgotPasswordActivity.this);
        }
        
        @Override
        public void onHide() {
            ViewUtils.enabledUserInteraction(ForgotPasswordActivity.this);
        }
    };
    
    @Inject
    ForgotPasswordViewModel mForgotPasswordViewModel;
    
    //====================== Override Base Method ======================//
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initDataBinding();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
        processingTaskFromViewModel();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mForgotPasswordViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mForgotPasswordViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(null);
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
    
    //====================== ViewBinding Method ======================//
    public void send(View view) {
        String email = mBinding.email.getText().toString();
        
        boolean isValid = validate(email);
        
        if (!isValid) {
            return;
        }
        
        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
        
        mForgotPasswordViewModel.forgotPassword(request);
    }
    
    //====================== Contract Method =========================//
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mBinding.progressBar.show();
            return;
        }
        mBinding.progressBar.hide();
    }
    
    @Override
    public void onSuccess(Object object) {
        MessageResponse messageResponse = (MessageResponse) object;
        String message = messageResponse.getMessage();
        
        Timber.tag(TAG).i(message);
        
        SnackBarUtils.showSnackBar(mBinding.mainLayout, message);
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).e(ex.getMessage());
        
        String message;
        
        if (ex instanceof RetrofitException) {
            message = getString(R.string.message_service_unavailable);
        } else {
            message = ex.getMessage();
        }
        
        SnackBarUtils.showSnackBar(mBinding.mainLayout, message);
        
    }
    //====================== Helper Method =========================//
    
    private void initDataBinding() {
        mBinding.progressBar.setListener(mProgressBarListener);
    }
    
    private boolean validate(String email) {
        if (ValidationHelper.isEmpty(email)) {
            mBinding.txtInputEmail.setError(getString(R.string.message_email_is_required));
            return false;
        }
        
        if (!ValidationHelper.isEmail(email)) {
            mBinding.txtInputEmail.setError(getString(R.string.message_email_is_invalid));
            return false;
        }
        
        mBinding.txtInputEmail.setErrorEnabled(false);
        
        return true;
    }
}
