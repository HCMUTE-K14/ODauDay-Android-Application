package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.NotificationService;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.FavoriteException;
import com.odauday.exception.NotifiException;
import com.odauday.model.RefreshTokenDevice;
import com.odauday.ui.alert.service.Notification;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by kunsubin on 6/5/2018.
 */

public class NotificationManagerRepository implements Repository {
    
    private final NotificationService mNotificationService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public NotificationManagerRepository(NotificationService notificationService,
        SchedulersExecutor schedulersExecutor) {
        mNotificationService = notificationService;
        mSchedulersExecutor = schedulersExecutor;
    }
    public Single<MessageResponse> sendNotification(Notification notification) {
        Single<JsonResponse<MessageResponse>> result = mNotificationService.sendNotification(notification);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new NotifiException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof NotifiException) {
                        throw ex;
                    }
                    throw new NotifiException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> saveRefreshTokemDevice(RefreshTokenDevice refreshTokenDevice) {
        Single<JsonResponse<MessageResponse>> result = mNotificationService.saveRefreshTokenDevice(refreshTokenDevice);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new NotifiException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof NotifiException) {
                        throw ex;
                    }
                    throw new NotifiException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
