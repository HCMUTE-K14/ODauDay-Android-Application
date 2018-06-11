package com.odauday.data.local.notification;

import com.odauday.data.local.tag.RecentTag;
import com.odauday.ui.alert.service.Notification;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by kunsubin on 6/4/2018.
 */

public interface NotificationService {
    Single<Long> create(Notification notification);
    Single<List<Notification>> findAllNotificationByUserId(String userId);
    Single<Long> tickEndNotification(Notification notification);
    Single<Long> clearAllNotification(List<Notification> list);
    Single<Long> getNumberNotification(String user_id);
}
