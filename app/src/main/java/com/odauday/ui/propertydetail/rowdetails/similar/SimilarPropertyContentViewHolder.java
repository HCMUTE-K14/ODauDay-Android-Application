package com.odauday.ui.propertydetail.rowdetails.similar;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import com.odauday.R;
import com.odauday.ui.propertydetail.common.SimilarProperty;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarPropertyContentViewHolder {
    
    View mRootView;
    
    LinearLayout mSimilarPropertyItemContainer;
    
    List<SimilarProperty> mSimilarProperties;
    
    SimilarPropertyContentViewHolderCallBack mCallBack;
    
    public SimilarPropertyContentViewHolder(View rootView,
        SimilarPropertyContentViewHolderCallBack callBack) {
        mRootView = rootView;
        mSimilarPropertyItemContainer = rootView.findViewById(R.id.horizontal_container);
        mCallBack = callBack;
    }
    
    @SuppressLint("CheckResult")
    public void bind() {
        getListSimilar()
            .observeOn(AndroidSchedulers.mainThread())
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
    
    
    private Single<List<SimilarProperty>> getListSimilar() {
        List<SimilarProperty> similarProperties = new ArrayList<>();
        
        SimilarProperty similarProperty = new SimilarProperty();
        similarProperty.setAddress("ADDRESSS");
        similarProperty.setNumOfBathroom(1);
        
        SimilarProperty similarProperty2 = new SimilarProperty();
        similarProperty2.setAddress("ADDRESSS");
        similarProperty2.setNumOfBathroom(1);
        
        SimilarProperty similarProperty3 = new SimilarProperty();
        similarProperty3.setAddress("ADDRESSS");
        similarProperty3.setNumOfBathroom(1);
        
        similarProperties.add(similarProperty);
        similarProperties.add(similarProperty2);
        similarProperties.add(similarProperty3);
        
        return Single.just(similarProperties);
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
