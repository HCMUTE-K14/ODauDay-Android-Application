package com.odauday.ui.search.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Html;
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
import com.odauday.config.AppConfig;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.SearchType;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by infamouSs on 4/10/18.
 */

public class InformationBar extends LinearLayout {
    
    private static final int DEBOUNCE_TIMEOUT = 500;
    
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
        RxView.clicks(getRootView())
                  .throttleFirst(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(success -> {
                      if (mListener != null &&
                          getContainerError().getVisibility() == View.VISIBLE) {
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
        setTextStatus(text, false);
    }
    
    public void setTextStatus(String text, boolean isHtml) {
        if (isHtml) {
            getTextViewStatus().setText(Html.fromHtml(text));
            return;
        }
        getTextViewStatus().setText(text);
        
    }
    
    public void updateWithSearchRequestAndSearchResult(SearchRequest searchRequest,
              SearchResult searchResult) {
        long totalCount = searchResult.getCount();
        int count = searchResult.getResult().size();
        StringBuilder builder = new StringBuilder();
        if (count == 0) {
            builder.append(getContext().getString(R.string.txt_no_properties_found));
        } else {
            if (totalCount > count) {
                builder.append("<b>")
                          .append(count)
                          .append("</b>")
                          .append(" ")
                          .append(getContext().getString(R.string.txt_of))
                          .append(" ");
                builder.append("<b>").append(totalCount).append(" </b>");
            } else {
                builder.append("<b>").append(count).append(" </b>");
            }
            
            builder.append(getResources()
                      .getQuantityString(R.plurals.property_plural, count));
            SearchCriteria searchCriteria = searchRequest.getCriteria();
            if (searchCriteria.getSearchType() != SearchType.ALL.getValue()) {
                SearchType searchType = SearchType.getByValue(searchCriteria.getSearchType());
                builder.append(" • ");
                builder.append(getContext().getString(searchType.getResourceId())).append("</b>");
            }
            
            List<String> inforBarFilters = new ArrayList<>();
            String priceRange = TextUtils
                      .getPriceText(searchCriteria.getPrice().getMin() * AppConfig.RATE_VND,
                                searchCriteria.getPrice().getMax() * AppConfig.RATE_VND, true);
            if (!TextUtils.isEmpty(priceRange)) {
                inforBarFilters.add(priceRange);
            }
            String bedRange = TextUtils
                      .getBedRangeDisplayString(searchCriteria.getBedrooms().getMin(),
                                searchCriteria.getBedrooms().getMax());
            String bathRange = TextUtils
                      .getBathRangeDisplayString(searchCriteria.getBathrooms().getMin(),
                                searchCriteria.getBathrooms().getMax());
            String parkingRange = TextUtils
                      .getParkingRangeDisplayString(searchCriteria.getParking().getMin());
            
            shortenAndAdd(inforBarFilters, bedRange);
            shortenAndAdd(inforBarFilters, bathRange);
            shortenAndAdd(inforBarFilters, parkingRange);
            if (!inforBarFilters.isEmpty()) {
                builder.append(" • ").append(android.text.TextUtils.join(" • ", inforBarFilters));
            }
        }
        setTextStatus(builder.toString(), true);
    }
    
    private void shortenAndAdd(List<String> infoBarFilters, String filterRangeDisplayString) {
        if (getContext() != null) {
            if (!TextUtils.isEmpty(filterRangeDisplayString) && !filterRangeDisplayString.trim()
                      .equals(getContext().getString(R.string.txt_any))) {
                String anyString = getContext().getString(R.string.txt_any);
                filterRangeDisplayString = filterRangeDisplayString
                          .replaceAll("-" + anyString, "+");
                if (!filterRangeDisplayString
                          .contains(getContext().getString(R.string.txt_studio))) {
                    filterRangeDisplayString = filterRangeDisplayString
                              .replaceAll(anyString + "-", "&lt;");
                }
                infoBarFilters.add(filterRangeDisplayString);
            }
        }
    }
    
    
    public RelativeLayout getContainerError() {
        return getRootView().findViewById(R.id.info_bar_container_error);
    }
    
    public TextView getTextViewError() {
        return getRootView().findViewById(R.id.txt_error_infor_bar);
    }
    
    public void setTextError(String text) {
        getTextViewError().setText(text);
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
