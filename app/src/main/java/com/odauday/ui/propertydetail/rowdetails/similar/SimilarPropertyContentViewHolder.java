package com.odauday.ui.propertydetail.rowdetails.similar;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.data.SimilarPropertyRepository;
import com.odauday.ui.propertydetail.common.SimilarProperty;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarPropertyContentViewHolder {
    
    View mRootView;
    
    LinearLayout mSimilarPropertyItemContainer;
    
    List<SimilarProperty> mSimilarProperties;
    
    SimilarPropertyContentViewHolderCallBack mCallBack;
    
    SimilarPropertyRepository mSimilarPropertyRepository;
    
    String mPropertyId;
    
    public SimilarPropertyContentViewHolder(View rootView,
        SimilarPropertyContentViewHolderCallBack callBack) {
        mRootView = rootView;
        mSimilarPropertyItemContainer = rootView.findViewById(R.id.horizontal_container);
        mCallBack = callBack;
    }
    
    public void setSimilarPropertyRepository(
        SimilarPropertyRepository similarPropertyRepository) {
        mSimilarPropertyRepository = similarPropertyRepository;
    }
    
    public void setPropertyId(String propertyId) {
        mPropertyId = propertyId;
    }
    
    @SuppressLint("CheckResult")
    public void bind() {
        if (mSimilarPropertyRepository != null && !TextUtils.isEmpty(mPropertyId)) {
            mSimilarPropertyRepository
                .get(mPropertyId)
                .doOnSubscribe(onSubscribe -> {
                    mCallBack.onLoading();
                })
                .subscribe(success -> {
                    if (success.isEmpty()) {
                        mCallBack.onFailure();
                        return;
                    }
                    createItem(success);
                    mCallBack.onSuccess();
                }, throwable -> {
                    mCallBack.onFailure();
                });
        } else {
            mCallBack.onFailure();
        }
    }
    
    public void unbind() {
        for (int i = 0; i < mSimilarPropertyItemContainer.getChildCount(); i++) {
            SimilarPropertyItemView view = (SimilarPropertyItemView) mSimilarPropertyItemContainer
                .getChildAt(i);
            
            view.unbind();
        }
        mSimilarPropertyItemContainer = null;
        mRootView = null;
        mSimilarProperties.clear();
        mSimilarProperties = null;
    }
    
    private void createItem(List<SimilarProperty> similarProperties) {
        mSimilarPropertyItemContainer.removeAllViews();
        for (SimilarProperty similarProperty : similarProperties) {
            SimilarPropertyItemView view = new SimilarPropertyItemView(mRootView.getContext());
            view.setData(similarProperty);
            view.bind();
            mSimilarPropertyItemContainer.addView(view);
        }
    }
    
    public View getRootView() {
        return mRootView;
    }
    
    public interface SimilarPropertyContentViewHolderCallBack {
        
        void onSuccess();
        
        void onLoading();
        
        void onFailure();
    }
}
