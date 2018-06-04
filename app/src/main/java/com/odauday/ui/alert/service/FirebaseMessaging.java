package com.odauday.ui.alert.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 5/17/2018.
 */

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG=FirebaseMessaging.class.getSimpleName();
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification()!=null&&remoteMessage.getData().size()>0){
            if (mPreferencesHelper.get(PrefKey.USER_ID,"").equals(remoteMessage.getData().get("user_id").toString().trim())){
                Notification notification=new Notification();
                notification.setTitle(remoteMessage.getNotification().getTitle());
                notification.setBody(remoteMessage.getNotification().getBody());
                notification.setUserID(remoteMessage.getData().get("user_id").toString());
                notification.setPropertyID(remoteMessage.getData().get("property_id").toString());
                notification.setImage(remoteMessage.getData().get("image").toString());
                notification.setType(remoteMessage.getData().get("type").toString());
                notification.setDate(System.currentTimeMillis());
                showNotification(notification);
                Timber.tag(TAG).d("Notification: "+notification.toString());
            }
            Timber.tag(TAG).d("UserSer: "+mPreferencesHelper.get(PrefKey.USER_ID,""));
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_notification_alert)
            .setContentTitle(notification.getTitle())
            .setContentText(notification.getBody())
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);
    
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    
}
