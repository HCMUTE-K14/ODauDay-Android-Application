package com.odauday.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.odauday.R;

/**
 * Created by infamouSs on 4/2/18.
 */

public class BaseDialogFragment extends DialogFragment {
    
    private static final float MATCH_PARENT_WIDTH = 400.0f;
    
    protected Builder mDialogBuilder;

    private View mDialogView;
    private OnShowListener mOnShowListener;
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mDialogBuilder = setupDialog((AppCompatActivity) context);
    }
    
    
    public Dialog create() {
        final Dialog dialogFragment = this.mDialogBuilder.create();
        dialogFragment.setCanceledOnTouchOutside(true);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final float screenWidthDp = ((float) displayMetrics.widthPixels) / displayMetrics.density;
        dialogFragment.setOnShowListener((DialogInterface dialog) -> {
            LayoutParams lp = new LayoutParams();
            lp.copyFrom(dialogFragment.getWindow().getAttributes());
            if (screenWidthDp < BaseDialogFragment.MATCH_PARENT_WIDTH) {
                lp.width = -1;
            }
            dialogFragment.getWindow().setAttributes(lp);
            if (BaseDialogFragment.this.mOnShowListener != null) {
                BaseDialogFragment.this.mOnShowListener.onShow(dialog);
            }
        });
        return dialogFragment;
    }
    
    protected void setTitle(String title) {
        ((TextView) this.mDialogView.findViewById(R.id.dialog_txt_header)).setText(title);
    }
    
    protected void setPositiveAlertDialogButton(String text, OnClickListener listener) {
        this.mDialogBuilder.setPositiveButton(text, listener);
    }
    
    protected void setNegativeAlertDialogButton(String text, OnClickListener listener) {
        this.mDialogBuilder.setNegativeButton(text, listener);
    }
    
    protected void setPositiveButton(String text, boolean isShowNegativeButton,
              View.OnClickListener listener) {
        boolean isVisibleLayoutButton = this.mDialogView
                                                  .findViewById(R.id.dialog_layout_btn)
                                                  .getVisibility() == View.VISIBLE;
        if (!isVisibleLayoutButton) {
            this.mDialogView.findViewById(R.id.dialog_layout_btn)
                      .setVisibility(View.VISIBLE);
        }
        
        this.mDialogView.findViewById(R.id.dialog_btn_cancel)
                  .setVisibility(isShowNegativeButton ? View.VISIBLE : View.GONE);
        
        Button btn = this.mDialogView.findViewById(R.id.dialog_btn_ok);
        btn.setText(text);
        btn.setOnClickListener(listener);
    }
    
    protected void setNegativeButton(String text, boolean isShowPositiveButton,
              View.OnClickListener listener) {
        boolean isVisibleLayoutButton = this.mDialogView
                                                  .findViewById(R.id.dialog_layout_btn)
                                                  .getVisibility() == View.VISIBLE;
        if (!isVisibleLayoutButton) {
            this.mDialogView.findViewById(R.id.dialog_layout_btn)
                      .setVisibility(View.VISIBLE);
        }
        this.mDialogView.findViewById(R.id.dialog_btn_ok)
                  .setVisibility(isShowPositiveButton ? View.VISIBLE : View.GONE);
        
        Button btn = this.mDialogView.findViewById(R.id.dialog_btn_cancel);
        btn.setText(text);
        btn.setOnClickListener(listener);
    }
    
    protected void setContent(View v) {
        ((ViewGroup) this.mDialogView.findViewById(R.id.dialog_content)).addView(v);
    }
    
    protected void setOnShowDialog(OnShowListener listener) {
        this.mOnShowListener = listener;
    }
    
    public Builder setupDialog(Activity activity) {
        Builder builder = new Builder(activity);
        this.mDialogView = View.inflate(activity, R.layout.layout_dialog_template, null);
        builder.setView(this.mDialogView);
        this.mDialogView.findViewById(R.id.dialog_layout_btn)
            .setVisibility(View.GONE);
        return builder;
    }
}
