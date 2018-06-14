package com.odauday.ui.alert.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.NotificationRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.ui.alert.ActivityDetailNotification;
import com.odauday.ui.alert.Demo;
import com.odauday.utils.ImageLoader;
import com.odauday.viewmodel.model.Resource;
import dagger.android.AndroidInjection;
import java.util.Random;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/**
 * Created by kunsubin on 5/17/2018.
 */

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG=FirebaseMessaging.class.getSimpleName();
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Inject
    NotificationRepository mNotificationRepository;
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification()!=null&&remoteMessage.getData().size()>0){
                Notification notification=new Notification();
                notification.setTitle(remoteMessage.getNotification().getTitle());
                notification.setBody(remoteMessage.getNotification().getBody());
                if(remoteMessage.getData().get("user_id").toString().equals("")){
                    notification.setUserID(mPreferencesHelper.get(PrefKey.USER_ID,""));
                }else {
                    notification.setUserID(remoteMessage.getData().get("user_id"));
                }
                notification.setPropertyID(remoteMessage.getData().get("property_id").toString());
                notification.setImage(remoteMessage.getData().get("image").toString());
                notification.setType(remoteMessage.getData().get("type").toString());
                notification.setDate(System.currentTimeMillis());
                showNotification(notification);
                
                putNotificationToLocal(notification);
                
                //postNumberNotification();
                EventBus.getDefault().post(new NotificationEvent<Notification>(2,notification));
                
                Timber.tag(TAG).d("RefreshTokenDevice: "+notification.toString());
        }
    }
    
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
    
    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }
    
    private void showNotification(Notification notification){
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ActivityDetailNotification.class);
        intent.putExtra("notification",notification);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        String NOTIFICATION_CHANNEL_ID = "01";
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Channel 1"
                ,
                NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification_alert)
            .setContentTitle(notification.getTitle())
            .setWhen(notification.getDate())
            .setContentText(notification.getBody())
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);
        
        notificationManager.notify(new Random().nextInt(10), notificationBuilder.build());
    }
    
    private void putNotificationToLocal(Notification notification){
        mNotificationRepository.create(notification)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribePutNotificationToLocal");
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("successPutNotificationToLocal");
                postNumberNotification();
            }, error -> {
                Timber.tag(TAG).d("PutNotificationToLocal "+error.getMessage());
            });
    }
    private void postNumberNotification(){
        String user_id=mPreferencesHelper.get(PrefKey.USER_ID,"");
        mNotificationRepository.getNumberNotification(user_id)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(TAG).d("doOnSubscribe");
            })
            .subscribe(success -> {
                Timber.tag(TAG).d("success: "+success);
                EventBus.getDefault().post(new NotificationEvent<Long>(1,success));
            }, error -> {
                Timber.tag(TAG).d(error.getMessage());
            });
    }
    
    
}
