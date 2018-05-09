package com.odauday.ui.propertydetail.rowdetails.direction;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.common.CardViewHasOption;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DirectionDetailViewHolder extends CardViewHasOption<DirectionDetailRow> {
    
    ViewStub mViewStubForm;
    Button mActionButton;
    
    DirectionFormViewHolder mDirectionForm;
    
    PropertyDetail mData;
    
    public DirectionDetailViewHolder(View view) {
        super(view, false);
        init();
    }
    
    public DirectionDetailViewHolder(View view, boolean isHiddenAble) {
        super(view, isHiddenAble);
        init();
    }
    
    public void init() {
        mViewStubForm = itemView.findViewById(R.id.add_location_form);
        mActionButton = itemView.findViewById(R.id.action_button);
        mActionButton.setOnClickListener(this::onActionButtonClick);
    }
    
    @Override
    protected void update(DirectionDetailRow directionDetailRow) {
        mData = directionDetailRow.getData();
        setRow(directionDetailRow);
        
        toggleForm(((DirectionDetailRow) getRow()).isShowDirectionForm());
        
        if (mData == null) {
            return;
        }
        if (mDirectionForm != null) {
            mDirectionForm.update(mData);
        }
    }
    
    @Override
    public void unbind() {
        mDirectionForm = null;
        mViewStubForm = null;
        mActionButton = null;
        
        if (mDirectionForm != null) {
            mDirectionForm.unbind();
        }
        
        mDirectionForm = null;
    }
    
    @Override
    protected int getMenuResId() {
        return R.menu.popup_menu_direction;
    }
    
    private void toggleForm(boolean isShow) {
        ((DirectionDetailRow) getRow()).setShowDirectionForm(isShow);
        if (isShow) {
            Timber.d(isShow + "");
            if (mData == null) {
                return;
            }
            if (mDirectionForm == null) {
                mDirectionForm = new DirectionFormViewHolder(mViewStubForm.inflate());
            }
            mDirectionForm.getParent().setVisibility(View.VISIBLE);
            mDirectionForm.getParent().setBackgroundColor(
                ContextCompat.getColor(itemView.getContext(), R.color.background_form));
            mDirectionForm.update(mData);
            return;
        }
        if (mDirectionForm != null) {
            mDirectionForm.getParent().setVisibility(View.GONE);
        }
    }
    
    private void showForm() {
        toggleForm(true);
    }
    
    private void hideForm() {
        toggleForm(false);
    }
    
    public void onActionButtonClick(View v) {
        boolean isShowForm = ((DirectionDetailRow) getRow()).isShowDirectionForm();
        
        if (isShowForm) {
            
            return;
        }
        showForm();
        mActionButton.setText(R.string.txt_save_location);
    }
}
