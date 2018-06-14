package com.odauday.ui.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import com.google.firebase.iid.FirebaseInstanceId;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.NotificationRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.notification.NotificationEntity;
import com.odauday.databinding.FragmentAlertTabMainBinding;
import com.odauday.ui.ClearMemory;
import com.odauday.ui.alert.service.Notification;
import com.odauday.ui.alert.service.NotificationEvent;
import com.odauday.ui.base.BaseContract;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.ValidationHelper;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AlertTabMainFragment extends BaseMVVMFragment<FragmentAlertTabMainBinding> implements
                                                                                        BaseContract,ClearMemory{
    
    public static final String TAG = AlertTabMainFragment.class.getSimpleName();
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    private AlertAdapter mAlertAdapter;
    private AlertEmptyAdapter mAlertEmptyAdapter;
    private RecyclerView mRecyclerView;
    
    ItemTouchHelper.SimpleCallback mSimpleCallback=new SimpleCallback(ItemTouchHelper.RIGHT,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            Timber.tag(TAG).d("onMove");
            return false;
        }
    
        @Override
        public void onSwiped(ViewHolder viewHolder, int direction) {
            Timber.tag(TAG).d("onSwiped");
            int position = viewHolder.getAdapterPosition();
            //update status sqlite
            tickEndNotification(mAlertAdapter.getItem(position));
            //remove adapter
            mAlertAdapter.removeItemData(position);
            onPostShowNumberNotification();
            if(mAlertAdapter.getItemCount()==0){
                mRecyclerView.setAdapter(mAlertEmptyAdapter);
            }
        }
    };
    private AlertAdapter.OnClickActionNotificationListener mOnClickActionNotificationListener=notification -> {
        Timber.tag(TAG).d("Dismiss: "+notification.getId());
        tickEndNotification(notification);
        //remove adapter
        mAlertAdapter.removeItemData(notification);
        onPostShowNumberNotification();
        if(mAlertAdapter.getItemCount()==0){
            mRecyclerView.setAdapter(mAlertEmptyAdapter);
        }
    };
    private AlertAdapter.OnClickItemNotificationListener mOnClickItemNotificationListener=notification -> {
        Timber.tag(TAG).d("Dismiss: "+notification.getId());
        Intent intent = new Intent(getContext(), ActivityDetailNotification.class);
        intent.putExtra("notification", notification);
        getActivity().startActivity(intent);
    };
    private ItemTouchHelper mItemTouchHelper=new ItemTouchHelper(mSimpleCallback);
    @Inject
    AlertTabViewModel mAlertTabViewModel;
    @Inject
    NotificationRepository mNotificationRepository;
   
    public static AlertTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AlertTabMainFragment fragment = new AlertTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        mAlertTabViewModel.findAllNotificationByUserId(mPreferencesHelper.get(PrefKey.USER_ID,""));
    }
    
    private void init() {
        mBinding.get().setHandler(this);
        mAlertAdapter=new AlertAdapter();
        mAlertAdapter.setOnClickActionNotificationListener(mOnClickActionNotificationListener);
        mAlertAdapter.setOnClickItemNotificationListener(mOnClickItemNotificationListener);
        mAlertEmptyAdapter=new AlertEmptyAdapter();
        mRecyclerView=mBinding.get().recycleViewNotification;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_alert_tab_main;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mAlertTabViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        onFailure((Exception) resource.data);
                        loading(false);
                        break;
                    case SUCCESS:
                        onSuccess(resource.data);
                        loading(false);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    public void loading(boolean isLoading) {
        Timber.tag(TAG).d("loading");
    }
    
    @Override
    public void onSuccess(Object object) {
        Timber.tag(TAG).d("success");
        List<Notification> list=(List<Notification>) object;
        if(!ValidationHelper.isEmptyList(list)){
            if(!(mRecyclerView.getAdapter() instanceof AlertAdapter)){
                mRecyclerView.setAdapter(mAlertAdapter);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            }
            mAlertAdapter.setData(list);
        }else {
            mRecyclerView.setAdapter(mAlertEmptyAdapter);
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).d("error:"+ex.getMessage());
        mRecyclerView.setAdapter(mAlertEmptyAdapter);
    }
    private void tickEndNotification(Notification notification){
        mNotificationRepository.tickEndNotification(notification)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("success tick end: "+notification.getId());
            
            }, error -> {
                Timber.tag(TAG).d("error tick end: "+error.getMessage());
            });
    }
    public void onClickClearAllNotification(){
        List<Notification> list=mAlertAdapter.getData();
        if(!ValidationHelper.isEmptyList(list)){
            mNotificationRepository.clearAllNotification(list)
                .doOnSubscribe(onSubscribe -> {
                    Timber.tag(TAG).d("doOnSubscribe");
                })
                .subscribe(success -> {
                    Timber.tag(TAG).d("success clear all notification");
                    mAlertAdapter.clearData();
                    if(mAlertAdapter.getItemCount()==0){
                        mRecyclerView.setAdapter(mAlertEmptyAdapter);
                    }
                    EventBus.getDefault().post(new NotificationEvent<Long>(1,Long.valueOf(0)));
                }, error -> {
                    Timber.tag(TAG).d("error clear all notification"+error.getMessage());
                });
        }
        
    }
    private void onPostShowNumberNotification(){
        String user_id=mPreferencesHelper.get(PrefKey.USER_ID,"");
        mNotificationRepository.getNumberNotification(user_id)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("onSuccess");
                EventBus.getDefault().post(new NotificationEvent<Long>(1,success));
            }, error -> {
                Timber.tag(TAG).d("error notification"+error.getMessage());
            });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationEvent(NotificationEvent notificationEvent){
        Timber.tag(MainActivity.class.getSimpleName()).d("NotificationEvent");
        if(notificationEvent.getCode()==2){
           Notification notification=(Notification) notificationEvent.getData();
           mAlertAdapter.putNotification(notification);
        }
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    
    @Override
    public void onDestroy() {
        clearMemory();
        super.onDestroy();
    }
    
    @Override
    public void clearMemory() {
        mAlertAdapter=null;
        mAlertEmptyAdapter=null;
        mRecyclerView=null;
    }
}
