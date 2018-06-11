package com.odauday.ui.alert;

import com.odauday.data.NotificationRepository;
import com.odauday.ui.alert.service.Notification;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AlertTabViewModel extends BaseViewModel {
    private static final String TAG=AlertTabViewModel.class.getSimpleName();
    NotificationRepository mNotificationRepository;
    
    @Inject
    public AlertTabViewModel(NotificationRepository notificationRepository) {
        mNotificationRepository = notificationRepository;
    }
    public void create(Notification notification){
        Disposable disposable = mNotificationRepository.create(notification)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
                response.setValue(Resource.loading(null));
               
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("success");
                response.setValue(Resource.success("",success));
                
            }, error -> {
                Timber.tag(TAG).d(error.getMessage());
                response.setValue(Resource.error("",error));
            });
        mCompositeDisposable.add(disposable);
    }
    public void findAllNotificationByUserId(String user_id){
        Disposable disposable = mNotificationRepository.findAllNotificationByUserId(user_id)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag("AlertTabViewModel").d("doOnSubscribe");
                response.setValue(Resource.loading(null));
            
            })
            .subscribe(success -> {
                Timber.tag("AlertTabViewModel").d("success");
                response.setValue(Resource.success("",success));
            
            }, error -> {
                Timber.tag("AlertTabViewModel").d(error.getMessage());
                response.setValue(Resource.error("",error));
            });
        mCompositeDisposable.add(disposable);
    }
    
    public void tickEndNotification(Notification notification){
        Disposable disposable = mNotificationRepository.tickEndNotification(notification)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
                response.setValue(Resource.loading(null));
            
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("success");
                response.setValue(Resource.success("",success));
            
            }, error -> {
                Timber.tag(TAG).d(error.getMessage());
                response.setValue(Resource.error("",error));
            });
        mCompositeDisposable.add(disposable);
    }
    public void clearAllNotification(List<Notification> list){
        Disposable disposable = mNotificationRepository.clearAllNotification(list)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("success");
                response.setValue(Resource.success("",success));
            }, error -> {
                Timber.tag(TAG).d(error.getMessage());
                response.setValue(Resource.error("",error));
            });
        mCompositeDisposable.add(disposable);
    }
}
