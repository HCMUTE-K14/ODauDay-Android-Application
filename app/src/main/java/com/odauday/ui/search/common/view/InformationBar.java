package com.odauday.ui.search.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.jakewharton.rxbinding2.view.RxView;
import com.odauday.R;
import com.odauday.utils.ViewUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;

/**
 * Created by infamouSs on 4/10/18.
 */

public class InformationBar extends LinearLayout {
    
    private static final int DEBOUNCE_TIMEOUT = 1000;
    private InformationBarListener mListener;
    private boolean mIsShowErrorContainer = false;
    
    public InformationBar(Context context) {
        super(context);
        init(context);
    }
    
    public InformationBar(Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        
    }
    
    public InformationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        inflater.inflate(R.layout.layout_infor_bar, this, true);
        
        setup(context, getMainView());
        
        setupListener();
    }
    
    private void setup(Context context, ViewAnimator inforBar) {
        inforBar.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        inforBar.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
        inforBar.setVisibility(View.VISIBLE);
        Drawable drawable = getResources()
                  .getDrawable(R.drawable.progress_indeterminate_infor_bar);
        drawable.setCallback(null);
        getProgressBar().setIndeterminateDrawable(drawable);
        getImageLighting().startAnimation(
                  AnimationUtils.loadAnimation(context, R.anim.fade_out_reverse_repeat));
    }
    
    @SuppressLint("CheckResult")
    private void setupListener() {
        RxView.clicks(getContainerError())
                  .throttleFirst(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(success -> {
                      if (mListener != null) {
                          mListener.onClickReload();
                      }
                  });
        getButtonSort().setOnClickListener(btnSort -> {
            if (mListener != null) {
                mListener.onCLickSort();
            }
        });
        
        getButtonSaveSearch().setOnClickListener(saveSearchBtn -> {
            if (mListener != null) {
                mListener.onClickSaveSearch();
            }
        });
    }
    
    public void showHideButtonSort(boolean show) {
        ViewUtils.showHideView(getButtonSort(), show);
    }
    
    public void showHideErrorContainer(boolean show) {
        mIsShowErrorContainer = show;
        hideProgressBar();
        ViewUtils.showHideView(getContainerError(), show);
        ViewUtils.showHideView(getTextViewStatus(), !show);
        ViewUtils.showHideView(getButtonSort(), !show);
        ViewUtils.showHideView(getButtonSaveSearch(), !show);
    }
    
    public void showProgressBar() {
        getMainView().setDisplayedChild(1);
    }
    
    public void hideProgressBar() {
        getMainView().setDisplayedChild(0);
    }
    
    public void setTextStatus(String text) {
        getTextViewStatus().setText(text);
    }
    
    public RelativeLayout getContainerError() {
        return getRootView().findViewById(R.id.info_bar_container_error);
    }
    
    public TextView getTextViewError() {
        return getRootView().findViewById(R.id.txt_error_infor_bar);
    }
    
    public ImageView getImageLighting() {
        return getRootView().findViewById(R.id.image_lighting);
    }
    
    public TextView getTextViewStatus() {
        return getRootView().findViewById(R.id.txt_status);
    }
    
    public Button getButtonSort() {
        return getRootView().findViewById(R.id.btn_sort);
    }
    
    public Button getButtonSaveSearch() {
        return getRootView().findViewById(R.id.btn_save_search);
    }
    
    public ProgressBar getProgressBar() {
        return (ProgressBar) getRootView().findViewById(R.id.progress_bar_infor_bar);
    }
    
    public ViewAnimator getMainView() {
        return getRootView().findViewById(R.id.main_view);
    }
    
    public void setListener(InformationBarListener listener) {
        mListener = listener;
    }
    
    public boolean isShowErrorContainer() {
        return mIsShowErrorContainer;
    }
    
    public interface InformationBarListener {
        
        void onClickSaveSearch();
        
        void onCLickSort();
        
        void onClickReload();
    }
}
