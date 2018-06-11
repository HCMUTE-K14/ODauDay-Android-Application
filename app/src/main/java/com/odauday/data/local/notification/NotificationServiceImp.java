package com.odauday.data.local.notification;

import com.odauday.data.local.notification.NotificationEntityDao.Properties;
import com.odauday.ui.alert.service.Notification;
import com.odauday.utils.TextUtils;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by kunsubin on 6/4/2018.
 */

public class NotificationServiceImp implements NotificationService {
    
    private final NotificationEntityDao mNotificationEntityDao;
    
    
    public NotificationServiceImp(NotificationEntityDao notificationEntityDao) {
        mNotificationEntityDao = notificationEntityDao;
    }
    
    @Override
    public Single<Long> create(Notification notification) {
        NotificationEntity notificationEntity=new NotificationEntity();
        notificationEntity.setId(TextUtils.generatorUUID());
        notificationEntity.setTitle(notification.getTitle());
        notificationEntity.setBody(notification.getBody());
        notificationEntity.setUserID(notification.getUserID());
        notificationEntity.setPropertyID(notification.getPropertyID());
        notificationEntity.setImage(notification.getImage());
        notificationEntity.setType(notification.getType());
        notificationEntity.setDate(notification.getDate());
        notificationEntity.setStatus(Status.OPEN);
        return Single.fromCallable(() -> mNotificationEntityDao.insert(notificationEntity));
    }
    
    @Override
    public Single<List<Notification>> findAllNotificationByUserId(String userId) {
        return Single.fromCallable(() -> {
            List<NotificationEntity> notificationEntities = mNotificationEntityDao
                .queryBuilder()
                .where(Properties.MUserID.eq(userId))
                .where(Properties.MStatus.eq(Status.OPEN))
                .orderDesc(Properties.MDate)
                .list();
            return convert(notificationEntities);
        });
    }
    
    @Override
    public Single<Long> tickEndNotification(Notification notification) {
        NotificationEntity notificationEntity=convertToNotificationEntity(notification);
        return Single.fromCallable(() -> updateNotification(notificationEntity));
    }
    
    @Override
    public Single<Long> clearAllNotification(List<Notification> list) {
        return Single.fromCallable(() -> clearAll(list));
    }
    
    @Override
    public Single<Long> getNumberNotification(String user_id) {
        return Single.fromCallable(() ->{
            long number=mNotificationEntityDao
                        .queryBuilder()
                        .where(Properties.MUserID.eq(user_id))
                        .where(Properties.MStatus.eq(Status.OPEN))
                        .count();
            return Long.valueOf(number);
        });
    }
    private Long clearAll(List<Notification> list){
        try {
            for (Notification notification:list) {
                NotificationEntity notificationEntity=convertToNotificationEntity(notification);
                mNotificationEntityDao.update(notificationEntity);
            }
            return Long.valueOf(1);
        }catch (DaoException ex){
            return Long.valueOf(-1);
        }
    }
    private Long updateNotification(NotificationEntity notificationEntity){
        try{
            mNotificationEntityDao.update(notificationEntity);
            return Long.valueOf(1);
        }catch (DaoException ex){
            return Long.valueOf(-1);
        }
    }
    private NotificationEntity convertToNotificationEntity(Notification notification){
        NotificationEntity notificationEntity=new NotificationEntity();
        notificationEntity.setId(notification.getId());
        notificationEntity.setTitle(notification.getTitle());
        notificationEntity.setBody(notification.getBody());
        notificationEntity.setUserID(notification.getUserID());
        notificationEntity.setPropertyID(notification.getPropertyID());
        notificationEntity.setImage(notification.getImage());
        notificationEntity.setType(notification.getType());
        notificationEntity.setDate(notification.getDate());
        notificationEntity.setStatus(Status.CLOSE);
        return notificationEntity;
    }
    private List<Notification> convert(List<NotificationEntity> notificationEntities) {
        List<Notification> list=new ArrayList<>();
        for (NotificationEntity notificationEntity:notificationEntities) {
            list.add(convertToNotification(notificationEntity));
        }
        return list;
    }
    
    private Notification convertToNotification(NotificationEntity notificationEntity) {
        Notification notification=new Notification();
        notification.setId(notificationEntity.getId());
        notification.setTitle(notificationEntity.getTitle());
        notification.setBody(notificationEntity.getBody());
        notification.setUserID(notificationEntity.getUserID());
        notification.setPropertyID(notificationEntity.getPropertyID());
        notification.setImage(notificationEntity.getImage());
        notification.setType(notificationEntity.getType());
        notification.setDate(notificationEntity.getDate());
        return notification;
    }
    
    
}
