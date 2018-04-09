package com.odauday.ui.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.data.remote.model.ErrorResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.user.model.RegisterRequest;
import com.odauday.databinding.ActivityRegisterBinding;
import com.odauday.exception.BaseException;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.view.MyProgressBar.MyProgressBarListener;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/27/18.
 */

public class RegisterActivity extends
                              BaseMVVMActivity<ActivityRegisterBinding> implements
                                                                        RegisterContract {

    //====================== Variable ======================//
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private final MyProgressBarListener mProgressBarListener = new MyProgressBarListener() {
        @Override
        public void onShow() {
            ViewUtils.disabledUserInteraction(RegisterActivity.this);
        }

        @Override
        public void onHide() {
            ViewUtils.enabledUserInteraction(RegisterActivity.this);
        }
    };
    @Inject
    RegisterViewModel mRegisterViewModel;

    //====================== Override Base Method ======================//
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mRegisterViewModel;
    }

    @Override
    protected void processingTaskFromViewModel() {
        mRegisterViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(resource.data);
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

    public void register(View view) {
        if (view.getId() != R.id.btn_register) {
            return;
        }
        doRegister();
    }

    public void login(View view) {
        openLoginActivity();
        close();
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
    public void onSuccess(Object object) {
        String message = ((MessageResponse) object).getMessage();
        Timber.tag(TAG).i(message);
    }

    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).e(ex.getMessage());

        List<ErrorResponse> errors = ((BaseException) ex).getErrors();
        String message;
        if (errors == null || errors.isEmpty()) {
            message = getString(R.string.message_registration_failed);
        } else {
            message = errors.get(0).getMessage();
        }
        SnackBarUtils.showSnackBar(mBinding.mainLayout, message);
    }

    //====================== Helper Method ======================//

    private void initDataBinding() {
        mBinding.progressBar.setListener(mProgressBarListener);
        mBinding.btnBack.setOnClickListener(view -> close());
    }

    private void openLoginActivity() {
        ViewUtils.startActivity(this, LoginActivity.class);
    }

    private boolean validate(String email, String displayName, String password, String rePassword) {
        mBinding.txtInputEmail.setErrorEnabled(false);
        mBinding.txtInputPassword.setErrorEnabled(false);
        mBinding.txtInputDisplayName.setErrorEnabled(false);
        mBinding.txtInputRePassword.setErrorEnabled(false);

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

        if (ValidationHelper.isEmpty(displayName)) {
            mBinding.txtInputDisplayName
                      .setError(getString(R.string.message_display_name_is_required));
            mBinding.displayName.requestFocus();
            return false;
        }

        if (ValidationHelper.isEmpty(password)) {
            mBinding.txtInputPassword.setError(getString(R.string.message_password_is_required));
            mBinding.password.requestFocus();
            return false;
        }

        if (ValidationHelper.isEmpty(rePassword)) {
            mBinding.txtInputRePassword
                      .setError(getString(R.string.message_re_password_is_required));
            mBinding.rePassword.requestFocus();

            return false;
        }

        if (!ValidationHelper.isEqual(password, rePassword)) {
            mBinding.txtInputRePassword
                      .setError(getString(R.string.message_re_password_do_not_match));
            mBinding.rePassword.requestFocus();

            return false;
        }

        return true;

    }

    private void doRegister() {
        String email = mBinding.email.getText().toString();
        String displayName = mBinding.displayName.getText().toString();
        String password = mBinding.password.getText().toString();
        String rePassword = mBinding.rePassword.getText().toString();

        boolean isValid = validate(email, displayName, password, rePassword);

        if (!isValid) {
            return;
        }

        RegisterRequest request = new RegisterRequest(email, password, displayName);

        mRegisterViewModel.register(request);
    }
}
