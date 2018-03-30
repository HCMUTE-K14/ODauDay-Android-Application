package com.odauday.ui.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.odauday.R;
import com.odauday.config.FacebookPermission;
import com.odauday.data.remote.model.users.AbstractAuthRequest;
import com.odauday.data.remote.model.users.FacebookAuthRequest;
import com.odauday.data.remote.model.users.NormalAuthRequest;
import com.odauday.databinding.ActivityLoginBinding;
import com.odauday.exception.RetrofitException;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.forgotpassword.ForgotPasswordActivity;
import com.odauday.ui.user.register.RegisterActivity;
import com.odauday.ui.view.MyProgressBar.MyProgressBarListener;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONException;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/24/18.
 */

public class LoginActivity extends BaseMVVMActivity<ActivityLoginBinding> implements
                                                                          LoginContract {
    
    //====================== Variable ======================//
    
    public static final String TAG = LoginActivity.class.getSimpleName();
    private final MyProgressBarListener mProgressBarListener = new MyProgressBarListener() {
        @Override
        public void onShow() {
            ViewUtils.disabledUserInteraction(LoginActivity.this);
        }
        
        @Override
        public void onHide() {
            ViewUtils.enabledUserInteraction(LoginActivity.this);
        }
    };
    
    @Inject
    LoginViewModel mLoginViewModel;
    
    private CallbackManager mCallbackManager;
    
    private String mFacebookAccessToken;
    
    private GraphRequest.GraphJSONObjectCallback mGraphCallBack = (object, response) -> {
        try {
            String facebookId = object.getString("id");
            String name = object.getString("name");
            String email = object.getString("email");
            
            AbstractAuthRequest request = new FacebookAuthRequest(facebookId, email, name,
                      mFacebookAccessToken);
            
            mLoginViewModel.login(request);
        } catch (JSONException e) {
            Timber.tag(TAG).e(e.getMessage());
        }
    };
    
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mFacebookAccessToken = loginResult.getAccessToken().getToken();
            
            Bundle parameters = new Bundle();
            parameters.putString("fields",
                      "id,name,email");
            GraphRequest request = GraphRequest
                      .newMeRequest(loginResult.getAccessToken(), mGraphCallBack);
            
            request.setParameters(parameters);
            request.executeAsync();
        }
        
        @Override
        public void onCancel() {
        
        }
        
        @Override
        public void onError(FacebookException error) {
            Timber.tag(TAG).e(error.getMessage());
            SnackBarUtils.showSnackBar(mBinding.mainLayout, error.getMessage());
        }
    };
    
    //====================== Override Base Method ======================//
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initDataBinding();
        initLoginWithFacebook();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mLoginViewModel;
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mLoginViewModel.response().observe(this, resource -> {
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
    
    public void login(View view) {
        if (view.getId() == R.id.btn_normal_login) {
            makeLoginNormalRequest();
        } else if (view.getId() == R.id.btn_login_with_facebook) {
            makeLoginWithFacebookRequest();
        }
    }
    
    public void register(View view) {
        openRegisterActivity();
    }
    
    public void forgotPassword(View view) {
        openForgotPasswordActivity();
    }
    
    public void close() {
        finish();
    }
    
    //====================== Contract Method ======================//
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mBinding.progressBar.show();
            return;
        }
        
        mBinding.progressBar.hide();
    }
    
    @Override
    public void onSuccess(Object data) {
        Timber.tag(TAG).i("Login Successfully");
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
    
    //====================== Helper Method ======================//
    
    private void initDataBinding() {
        mBinding.progressBar.setListener(mProgressBarListener);
        mBinding.btnBack.setOnClickListener(view -> close());
    }
    
    private void initLoginWithFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mBinding.btnRealLoginFacebook.registerCallback(mCallbackManager, mFacebookCallback);
    }
    
    private void makeLoginNormalRequest() {
        String email = mBinding.email.getText().toString();
        String password = mBinding.password.getText().toString();
        
        boolean isValidInput = validate(email, password);
        
        if (!isValidInput) {
            return;
        }
        
        AbstractAuthRequest request = new NormalAuthRequest(email, password);
        
        mLoginViewModel.login(request);
    }
    
    private void makeLoginWithFacebookRequest() {
        List<String> permissionNeeds = Arrays
                  .asList(FacebookPermission.EMAIL, FacebookPermission.PUBLIC_PROFILE);
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
    }
    
    private boolean validate(String email, String password) {
        
        mBinding.txtInputEmail.setErrorEnabled(false);
        mBinding.txtInputPassword.setErrorEnabled(false);
        
        if (ValidationHelper.isEmpty(email)) {
            mBinding.txtInputEmail.setError(getString(R.string.message_email_is_required));
            mBinding.email.requestFocus();
            return false;
        }
        
        if (!ValidationHelper.isEmail(email)) {
            mBinding.txtInputEmail.setError(getString(R.string.message_email_is_invalid));
            mBinding.email.requestFocus();
            return false;
        }
        
        if (ValidationHelper.isEmpty(password)) {
            mBinding.txtInputPassword.setError(getString(R.string.message_password_is_required));
            mBinding.password.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void openRegisterActivity() {
        ViewUtils.startActivity(this, RegisterActivity.class);
    }
    
    private void openForgotPasswordActivity() {
        ViewUtils.startActivity(this, ForgotPasswordActivity.class);
    }
}
