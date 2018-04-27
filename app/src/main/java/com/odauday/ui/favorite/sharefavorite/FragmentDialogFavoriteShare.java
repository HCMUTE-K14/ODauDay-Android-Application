package com.odauday.ui.favorite.sharefavorite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.databinding.FragmentDialogFavoriteShareBinding;
import com.odauday.model.ShareFavorite;
import com.odauday.utils.SnackBarUtils;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/12/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentDialogFavoriteShare extends DialogFragment {
    
    private static final String TAG = FragmentDialogFavoriteShare.class.getSimpleName();
    private FragmentDialogFavoriteShareBinding mFragmentDialogFavoriteShareBinding;
    private String message = "";
    private String mName;
    private String mEmail;
    private OnClickSendEmailListener mOnClickSendEmailListener;
    
    @SuppressLint("ValidFragment")
    public FragmentDialogFavoriteShare(String name, String email) {
        mName = name;
        mEmail = email;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        FragmentDialogFavoriteShareBinding binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_dialog_favorite_share, container, false);
        mFragmentDialogFavoriteShareBinding = binding;
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mFragmentDialogFavoriteShareBinding.setHandler(this);
        mFragmentDialogFavoriteShareBinding.txtYourName.setText(mName);
        mFragmentDialogFavoriteShareBinding.txtYourEmail.setText(mEmail);
    }
    
    public void onClickClose() {
        Timber.tag(TAG).d("Close");
        this.dismiss();
    }
    
    public void onClickSendEmail() {
        Timber.tag(TAG).d("Send email");
        if (checkInput()) {
            
            ShareFavorite shareFavorite = new ShareFavorite();
            shareFavorite.setEmail_friend(
                mFragmentDialogFavoriteShareBinding.txtEmailFriend.getText().toString().trim());
            shareFavorite.setName(
                mFragmentDialogFavoriteShareBinding.txtYourName.getText().toString().trim());
            shareFavorite.setEmail_from(
                mFragmentDialogFavoriteShareBinding.txtYourEmail.getText().toString().trim());
            
            mOnClickSendEmailListener.onClick(shareFavorite);
            
            this.dismiss();
        } else {
            SnackBarUtils.showSnackBar(mFragmentDialogFavoriteShareBinding.shareFavorite, message);
        }
    }
    
    private boolean checkInput() {
        if (mFragmentDialogFavoriteShareBinding.txtEmailFriend.getText().toString().trim()
            .equals("")) {
            message = getString(R.string.empty_email_friend);
            return false;
        }
        if (mFragmentDialogFavoriteShareBinding.txtYourName.getText().toString().trim()
            .equals("")) {
            message = getString(R.string.empty_your_name);
            return false;
        }
        if (mFragmentDialogFavoriteShareBinding.txtYourEmail.getText().toString().trim()
            .equals("")) {
            message = getString(R.string.empty_your_email);
            return false;
        }
        return true;
    }
    
    public void setOnClickSendEmailListener(
        OnClickSendEmailListener onClickSendEmailListener) {
        mOnClickSendEmailListener = onClickSendEmailListener;
    }
    
    public interface OnClickSendEmailListener {
        
        void onClick(ShareFavorite shareFavorite);
    }
}
