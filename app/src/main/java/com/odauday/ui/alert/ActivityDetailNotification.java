package com.odauday.ui.alert;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.PropertyRepository;
import com.odauday.databinding.ActivityDetailNotificationBinding;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.alert.service.Notification;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ValidationHelper;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 6/12/2018.
 */

public class ActivityDetailNotification extends BaseMVVMActivity<ActivityDetailNotificationBinding> {
    private static final String TAG=ActivityDetailNotification.class.getSimpleName();
    @Inject
    PropertyRepository mPropertyRepository;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_notification;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }
    
    private void getData() {
        mBinding.setHandler(this);
        Notification notification = getIntent().getExtras().getParcelable("notification");
        
        if(!ValidationHelper.isNull(notification)){
            mBinding.setNotification(notification);
            mPropertyRepository.getFullDetail(notification.getPropertyID())
                .doOnSubscribe(onSubscribe -> {
                    Timber.tag(TAG).d("onSubscribe");
                })
                .subscribe(success -> {
                    Timber.tag(TAG).d("onSuccess");
                    if(!ValidationHelper.isNull(success)){
                        mBinding.setPropertydetail(success);
                    }
                }, throwable -> {
                    mBinding.relativeDetailNotification.setVisibility(View.GONE);
                });
        }else {
            Timber.tag(TAG).d("Notification is null");
        }
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    public void onClickBack(View view){
        this.finish();
    }
    public void onClickMoreDetail(String id){
        if(!TextUtils.isEmpty(id)){
            PropertyDetail propertyDetail = new PropertyDetail();
            propertyDetail.setId(id);
            propertyDetail.setFavorite(false);
            Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, propertyDetail);
            startActivity(intent);
        }
    }
}
