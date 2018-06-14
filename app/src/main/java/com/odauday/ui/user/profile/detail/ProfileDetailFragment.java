package com.odauday.ui.user.profile.detail;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.Constants.Task;
import com.odauday.data.local.cache.UserPreferenceHelper;
import com.odauday.data.remote.user.model.ChangePasswordRequest;
import com.odauday.databinding.FragmentProfileDetailBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.user.buypoint.BuyPointActivity;
import com.odauday.ui.user.profile.ProfileUserViewModel;
import com.odauday.ui.user.profile.common.ChangePasswordDialog;
import com.odauday.ui.view.DialogWithTextBox;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/29/18.
 */
public class ProfileDetailFragment extends BaseMVVMFragment<FragmentProfileDetailBinding> implements
                                                                                          ProfileDetailContract {
    
    
    public static final String TAG = ProfileDetailFragment.class.getSimpleName();
    
    @Inject
    UserPreferenceHelper mUserPreferenceHelper;
    
    @Inject
    ProfileUserViewModel mProfileUserViewModel;
    
    
    ProgressDialog mProgressDialog;
    
    private User mCurrentUser;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_detail;
    }
    
    public static ProfileDetailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mProfileUserViewModel.response().observe(this, resource -> {
            if (resource != null) {
                String task = resource.task;
                
                switch (resource.status) {
                    case ERROR:
                        switch (task) {
                            case Task.TASK_UPDATE_PROFILE:
                                onFailureUpdateProfile();
                                break;
                            case Task.TASK_RESEND_ACTIVE_ACCOUNT:
                                loading(false);
                                onFailureReSendActivation();
                                break;
                            case Task.TASK_CHANGE_PASSWORD:
                                onFailureChangePassword();
                                break;
                            case Task.TASK_GET_AMOUNT:
                                onFailureGetAmount();
                                break;
                        }
                        break;
                    case SUCCESS:
                        switch (task) {
                            case Task.TASK_UPDATE_PROFILE:
                                onSuccessUpdateProfile();
                                break;
                            case Task.TASK_RESEND_ACTIVE_ACCOUNT:
                                loading(false);
                                onSuccessReSendActivation();
                                break;
                            case Task.TASK_CHANGE_PASSWORD:
                                onSuccessChangePassword();
                                break;
                            case Task.TASK_GET_AMOUNT:
                                onSuccessGetAmount((long) resource.data);
                                break;
                        }
                        break;
                    case LOADING:
                        if (task.equals(Task.TASK_RESEND_ACTIVE_ACCOUNT)) {
                            loading(true);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);
        
        mCurrentUser = mUserPreferenceHelper.getCurrentUser();
        initData();
        initListener();
    }
    
    private void initData() {
        mBinding.get().listProperty.setVisibility(View.GONE);
        mBinding.get().phone.setValue(mCurrentUser.getPhone());
        String facebookProfileUrl;
        if (TextUtils.isEmpty(mCurrentUser.getFacebookId())) {
            facebookProfileUrl = getString(R.string.n_a);
        } else {
            facebookProfileUrl = getString(R.string.facebook_url, mCurrentUser.getFacebookId());
        }
        mBinding.get().facebook.setValue(facebookProfileUrl);
        mBinding.get().role.setValue(TextUtils.capitalize(mCurrentUser.getRole()));
        if (mCurrentUser.getStatus().equals(User.STATUS_PENDING)) {
            mBinding.get().status.getSubText().setVisibility(View.VISIBLE);
            mBinding.get().status.setSubText(R.string.message_you_should_active_account);
        } else if (mCurrentUser.getStatus().equals(User.STATUS_DISABLED)) {
            mBinding.get().status.getSubText().setVisibility(View.VISIBLE);
            mBinding.get().status.setSubText(R.string.message_your_account_is_disabled);
        }
        mBinding.get().status.setValue(TextUtils.capitalize(mCurrentUser.getStatus()));
        mBinding.get().amount.setValue(String.valueOf(mCurrentUser.getAmount()));
        
    }
    
    private void initListener() {
        mBinding.get().phone.setListener(this::showDialogUpdatePhoneNumber);
        
        mBinding.get().logOut.setListener(this::showDialogConfirmLogout);
        
        mBinding.get().facebook.setListener(this::openFacebookUrl);
        
        mBinding.get().status.setListener(this::handlingClickStatusUser);
        
        mBinding.get().changePassword.setListener(this::showDialogChangePassword);
        
        mBinding.get().amount.setListener(() -> {
            mProfileUserViewModel.getAmount();
        });
        
        mBinding.get().subscriber.setListener(() -> {
            ViewUtils.startActivity(getActivity(), BuyPointActivity.class);
        });
    }
    
    
    private void showDialogUpdatePhoneNumber() {
        
        DialogWithTextBox changeDisplayNameDialog = DialogWithTextBox
            .newInstance(R.string.txt_update_phone, R.string.txt_phone_number, 0);
        changeDisplayNameDialog.setListener(phoneNumber -> {
            
            if (mCurrentUser != null) {
                if (ValidationHelper.isPhoneNumber(phoneNumber)) {
                    mCurrentUser.setPhone(phoneNumber);
                    mProfileUserViewModel.updateProfile(mCurrentUser);
                } else {
                    Toast.makeText(getContext(), R.string.message_phone_is_invalid,
                        Toast.LENGTH_SHORT)
                        .show();
                }
            }
            
        });
        changeDisplayNameDialog.setTargetFragment(null,
            DialogWithTextBox.REQUEST_CODE);
        if (getFragmentManager() != null) {
            changeDisplayNameDialog.show(getActivity().getSupportFragmentManager(), TAG);
        }
    }
    
    @Override
    public void onSuccessUpdateProfile() {
        initData();
        mUserPreferenceHelper.putCurrentUser(mCurrentUser);
    }
    
    @Override
    public void onFailureUpdateProfile() {
        Toast.makeText(getContext(), R.string.message_update_profile_failure, Toast.LENGTH_SHORT)
            .show();
    }
    
    @Override
    public void onSuccessReSendActivation() {
        Toast.makeText(getContext(), R.string.message_activation_link_has_been_sent_to_your_email,
            Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFailureReSendActivation() {
        Toast.makeText(getContext(), R.string.message_error_resend_activation, Toast.LENGTH_SHORT)
            .show();
    }
    
    @Override
    public void onSuccessChangePassword() {
        Toast.makeText(getContext(), R.string.message_password_is_updated,
            Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFailureChangePassword() {
        Toast.makeText(getContext(), R.string.message_error_change_password,
            Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onSuccessGetAmount(long amount) {
        mCurrentUser.setAmount(amount);
        mUserPreferenceHelper.putCurrentUser(mCurrentUser);
        mBinding.get().amount.setValue(String.valueOf(amount));
    }
    
    @Override
    public void onFailureGetAmount() {
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.setTitle(R.string.txt_wait_a_second);
            mProgressDialog.setMessage(getString(R.string.txt_sending_mail));
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
        
    }
    
    private void showDialogConfirmLogout() {
        AlertDialog.Builder dialog = new Builder(getActivity());
        dialog.setTitle(R.string.txt_wait_a_second)
            .setMessage(R.string.message_are_u_want_to_log_out)
            .setPositiveButton(R.string.txt_no, (dialog12, which) -> {
                dialog12.cancel();
                dialog12.dismiss();
            })
            .setNegativeButton(R.string.txt_yes,
                (dialog1, which) -> {
                    logOut();
                });
        
        dialog.create().show();
    }
    
    private void logOut() {
        if (getActivity() == null) {
            return;
        }
        mUserPreferenceHelper.logOut();
        Intent i = getActivity().getBaseContext().getPackageManager()
            .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    
    private void openFacebookUrl() {
        String url = mBinding.get().facebook.getValue();
        if (TextUtils.isEmpty(url)) {
            return;
        }
        
        if (url.equals(getString(R.string.n_a))) {
            return;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    
    private void handlingClickStatusUser() {
        String status = mCurrentUser.getStatus();
        if (status.equals(User.STATUS_PENDING)) {
            showDialogConfirmReSendRequestActiveAccount();
        }
    }
    
    private void showDialogConfirmReSendRequestActiveAccount() {
        AlertDialog.Builder dialog = new Builder(getActivity());
        dialog.setTitle(R.string.txt_wait_a_second)
            .setMessage(R.string.message_are_u_want_to_get_activation_link)
            .setPositiveButton(R.string.txt_no, (dialog12, which) -> {
                dialog12.cancel();
                dialog12.dismiss();
            })
            .setNegativeButton(R.string.txt_yes,
                (dialog1, which) -> {
                    mProfileUserViewModel.reSendActiveAccount(mCurrentUser.getEmail());
                });
        
        dialog.create().show();
    }
    
    private void showDialogChangePassword() {
        ChangePasswordDialog changePasswordDialog = ChangePasswordDialog.newInstance();
        changePasswordDialog.setOnChangePasswordListener((oldPassword, newPassword) -> {
            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setUserId(mCurrentUser.getId());
            request.setOldPassword(oldPassword);
            request.setNewPassword(newPassword);
            
            mProfileUserViewModel.changePassword(request);
        });
        
        changePasswordDialog.setTargetFragment(null,
            ChangePasswordDialog.REQUEST_CODE);
        if (getFragmentManager() != null) {
            changePasswordDialog.show(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
