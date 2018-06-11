package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.notification.NotificationService;
import com.odauday.exception.NotifiException;
import com.odauday.ui.alert.service.Notification;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 6/4/2018.
 */

public class NotificationRepository implements Repository {
    private final NotificationService mNotificationService;
    
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public NotificationRepository(
        NotificationService notificationService,
        SchedulersExecutor schedulersExecutor) {
        mNotificationService = notificationService;
        mSchedulersExecutor = schedulersExecutor;
    }
    public Single<Long> create(Notification notification){
        return mNotificationService
            .create(notification)
            .onErrorResumeNext(throwable -> {
                Timber.tag("NotificationRepository:").d(throwable.getMessage());
                throw new NotifiException(throwable.getMessage());
            })
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<List<Notification>> findAllNotificationByUserId(String user_id){
        return mNotificationService
            .findAllNotificationByUserId(user_id)
            .onErrorResumeNext(throwable -> {
                throw new NotifiException(throwable.getMessage());
            })
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<Long> tickEndNotification(Notification notification){
        return mNotificationService
            .tickEndNotification(notification)
            .onErrorResumeNext(throwable -> {
                Timber.tag("NotificationRepository:").d(throwable.getMessage());
                throw new NotifiException(throwable.getMessage());
            })
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<Long> clearAllNotification(List<Notification> list){
        return mNotificationService
            .clearAllNotification(list)
            .onErrorResumeNext(throwable -> {
                Timber.tag("NotificationRepository:").d(throwable.getMessage());
                throw new NotifiException(throwable.getMessage());
            })
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<Long> getNumberNotification(String user_id){
        return mNotificationService
            .getNumberNotification(user_id)
            .onErrorResumeNext(throwable -> {
                Timber.tag("NotificationRepository:").d(throwable.getMessage());
                throw new NotifiException(throwable.getMessage());
            })
            .subscribeOn(mSchedulersExecutor.computation())
            .observeOn(mSchedulersExecutor.ui());
    }
}
