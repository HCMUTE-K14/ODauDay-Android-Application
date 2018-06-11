package com.odauday.ui.propertydetail.rowdetails.similar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;
import com.odauday.R;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.similar.SimilarPropertyContentViewHolder.SimilarPropertyContentViewHolderCallBack;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarPropertyViewHolder extends BaseRowViewHolder<SimilarPropertyRow> implements
                                                                                     SimilarPropertyContentViewHolderCallBack {
    
    SimilarPropertyContentViewHolder mContentHolder;
    
    public SimilarPropertyViewHolder(View itemView) {
        super(itemView);
    }
    
    @Override
    protected void update(SimilarPropertyRow similarPropertyRow) {
        super.update(similarPropertyRow);
        
        if (mContentHolder == null) {
            mContentHolder = new SimilarPropertyContentViewHolder(
                View.inflate(itemView.getContext(), R.layout.row_similar_property_content, null),
                this);
            if(getRow().getData() != null){
                mContentHolder.setSimilarPropertyRepository(getRow().getSimilarPropertyRepository());
                mContentHolder.setPropertyId(getRow().getData().getId());
                ((ViewGroup) itemView).addView(mContentHolder.getRootView());
            }
        }
        mContentHolder.bind();
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mContentHolder.unbind();
        mContentHolder = null;
    }
    
    private void showProgress() {
        ((ViewAnimator) this.itemView).setDisplayedChild(0);
    }
    
    private void showSimilarPropertyData() {
        ((ViewAnimator) this.itemView).setDisplayedChild(1);
    }
    
    @Override
    public void onSuccess() {
        showSimilarPropertyData();
    }
    
    @Override
    public void onLoading() {
        showProgress();
    }
    
    @Override
    public void onFailure() {
        getRowControllerListener().removeRow(getRow());
    }
}
