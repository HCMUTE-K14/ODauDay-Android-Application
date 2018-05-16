package com.odauday.ui.propertydetail.rowdetails.direction;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.data.DirectionRepository;
import com.odauday.data.local.cache.DirectionsPreference;
import com.odauday.data.remote.direction.model.DirectionRequest;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.common.CardViewHasOption;
import com.odauday.ui.propertydetail.common.DirectionLocation;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DirectionDetailViewHolder extends CardViewHasOption<DirectionDetailRow> {
    
    private static final int LIMIT_DIRECTION = 4;
    
    private ViewStub mViewStubForm;
    private Button mActionButton;
    private LinearLayout mDirectContainer;
    
    private DirectionFormViewHolder mDirectionForm;
    
    private PropertyDetail mData;
    private DirectionsPreference mDirectionsPreference;
    private DirectionRepository mDirectionRepository;
    
    int mLocationAdded = 0;
    
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
        mDirectContainer = itemView.findViewById(R.id.directions_container);
        mActionButton.setOnClickListener(this::onActionButtonClick);
    }
    
    @Override
    protected boolean onMenuItemSelected(int idMenu) {
        if (idMenu == R.id.action_reset_directions_locations) {
            mDirectionsPreference.clearDirection();
            mLocationAdded = 0;
            update(getRow());
            getRowControllerListener().notifyDataChanged();
        }
        return super.onMenuItemSelected(idMenu);
    }
    
    @Override
    protected void update(DirectionDetailRow directionDetailRow) {
        super.update(directionDetailRow);
        
        mDirectionsPreference = ((DirectionDetailRow) getRow()).getDirectionsPreference();
        mDirectionRepository = ((DirectionDetailRow) getRow()).getDirectionRepository();
        
        mData = directionDetailRow.getData();
        setRow(directionDetailRow);
        
        toggleForm((getRow()).isShowDirectionForm());
        if (mData == null) {
            return;
        }
        if (mDirectionForm != null) {
            mDirectionForm.update(mData);
        }
        clearLocation();
        
        if (mDirectionsPreference.getDirection().size() < LIMIT_DIRECTION) {
            mActionButton.setVisibility(View.VISIBLE);
        }
        for (DirectionLocation location : mDirectionsPreference.getDirection()) {
            addLocation(location);
        }
    }
    
    
    private void clearLocation() {
        for (int i = mDirectContainer.getChildCount() - 1; i >= 0; i--) {
            Object tag = mDirectContainer.getChildAt(i).getTag();
            if (tag != null && tag instanceof DirectionLocation) {
                mDirectContainer.removeViewAt(i);
            }
        }
        mLocationAdded = 0;
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mDirectionForm = null;
        mViewStubForm = null;
        mActionButton = null;
        mData = null;
        
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
            if (mData == null) {
                return;
            }
            if (mDirectionForm == null) {
                mDirectionForm = new DirectionFormViewHolder(mViewStubForm.inflate());
            } else {
                mDirectionForm.getParent().setVisibility(View.VISIBLE);
            }
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
        StageRow stageRow = getRow().getStageRow();
        getRowControllerListener().notifyItemChanged(stageRow);
    }
    
    private void hideForm() {
        toggleForm(false);
        StageRow stageRow = getRow().getStageRow();
        getRowControllerListener().notifyItemChanged(stageRow);
    }
    
    public void onActionButtonClick(View v) {
        boolean isShowForm = ((DirectionDetailRow) getRow()).isShowDirectionForm();
        
        if (isShowForm) {
            if (mDirectionForm != null && mDirectionForm.isValidForm()) {
                final DirectionLocation directionLocation = new DirectionLocation();
                directionLocation.setFullAddress(mDirectionForm.getPlace().getName());
                directionLocation.setLabel(mDirectionForm.getPlace().getName().split(",")[0]);
                directionLocation.setToLocation(mDirectionForm.getPlace().getLocation());
                directionLocation.setMode(mDirectionForm.getMode());
                if (mDirectionsPreference.getDirection().contains(directionLocation)) {
                    Toast.makeText(itemView.getContext(), R.string.message_direction_is_already,
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                storedDirectionLocation(directionLocation);
                addLocation(directionLocation);
                if (mLocationAdded > LIMIT_DIRECTION - 1) {
                    hideForm();
                } else {
                    mActionButton.setText(R.string.txt_add_location);
                }
                return;
            }
        }
        showForm();
        mActionButton.setText(R.string.txt_save_location);
    }
    
    private DirectionRequest makeRequest(DirectionLocation directionLocation) {
        DirectionRequest request = new DirectionRequest();
        request.setFrom(mData.getLocation());
        request.setTo(directionLocation.getToLocation());
        request.setMode(directionLocation.getMode().getApiString());
        
        return request;
    }
    
    private void storedDirectionLocation(DirectionLocation location) {
        mDirectionsPreference.putDirection(location);
    }
    
    @SuppressLint("CheckResult")
    private void addLocation(DirectionLocation directionLocation) {
        DirectionItem view = new DirectionItem(mDirectContainer.getContext());
        
        mDirectionRepository.get(makeRequest(directionLocation))
            .doOnSubscribe(onSubscribe -> {
                mDirectContainer.addView(view, mLocationAdded + 2);
                mLocationAdded++;
                view.showProgress();
            })
            .doFinally(() -> {
                view.setTag(directionLocation);
                if (mLocationAdded > LIMIT_DIRECTION - 1) {
                    mActionButton.setVisibility(View.GONE);
                }
            })
            .subscribe(success -> {
                directionLocation.setArrivalTime(success.getDuration());
                directionLocation.setDistance(success.getDistance());
                directionLocation.setFromLocation(mData.getLocation());
                
                view.hideProgress();
                view.onSuccess(directionLocation);
                
            }, throwable -> {
                directionLocation.setDistance(0);
                directionLocation.setArrivalTime(0);
                directionLocation.setFromLocation(mData.getLocation());
                
                view.hideProgress();
                view.onError(directionLocation);
            });
    }
}
