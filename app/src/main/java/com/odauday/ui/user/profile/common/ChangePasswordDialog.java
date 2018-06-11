package com.odauday.ui.user.profile.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.utils.ValidationHelper;

/**
 * Created by infamouSs on 5/30/18.
 */
public class ChangePasswordDialog extends BaseDialogFragment {
    
    public static final int REQUEST_CODE = 199;

    EditText mOldPassword;
    EditText mNewPassword;
    EditText mRePassword;
    
    OnChangePasswordListener mOnChangePasswordListener;
    
    public static ChangePasswordDialog newInstance() {
        
        Bundle args = new Bundle();
        
        ChangePasswordDialog fragment = new ChangePasswordDialog();
        fragment.setArguments(args);
        return fragment;
    }
    
    
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_change_password));
        View v = View.inflate(getActivity(), R.layout.layout_dialog_change_password, null);
        
        mOldPassword = v.findViewById(R.id.old_password);
        mNewPassword = v.findViewById(R.id.new_password);
        mRePassword = v.findViewById(R.id.re_password);
        
        setContent(v);
        setNegativeAlertDialogButton(getString(R.string.txt_cancel), (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        
        setPositiveAlertDialogButton(getString(R.string.txt_save), (dialog, which) -> {
            if (mOnChangePasswordListener != null) {
                String oldPassword = mOldPassword.getText().toString();
                String newPassword = mNewPassword.getText().toString();
                String rePassword = mRePassword.getText().toString();
                
                boolean isValid = validate(oldPassword, newPassword, rePassword);
                if (isValid) {
                    mOnChangePasswordListener
                        .onChangePassword(oldPassword, newPassword);
                }
            }
        });
        
        return create();
    }
    
    public void setOnChangePasswordListener(
        OnChangePasswordListener onChangePasswordListener) {
        mOnChangePasswordListener = onChangePasswordListener;
    }
    
    private boolean validate(String oldPassword, String newPassword, String rePassword) {
        if (ValidationHelper.isEmpty(oldPassword)) {
            Toast.makeText(getContext(), R.string.message_password_is_required, Toast.LENGTH_SHORT)
                .show();
            return false;
        }
        
        if (ValidationHelper.isEmpty(newPassword)) {
            Toast.makeText(getContext(), R.string.message_password_is_required, Toast.LENGTH_SHORT)
                .show();
            return false;
        }
        
        if (ValidationHelper.isEmpty(rePassword)) {
            Toast.makeText(getContext(), R.string.message_re_password_is_required,
                Toast.LENGTH_SHORT)
                .show();
            return false;
        }
        
        if (!ValidationHelper.isEqual(newPassword, rePassword)) {
            Toast.makeText(getContext(), R.string.message_re_password_do_not_match,
                Toast.LENGTH_SHORT)
                .show();
            return false;
        }
        
        return true;
    }
    
    public interface OnChangePasswordListener {
        
        void onChangePassword(String oldPassword, String newPassword);
    }
}
