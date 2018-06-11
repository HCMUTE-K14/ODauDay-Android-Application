package com.odauday.data.remote;

import static com.odauday.api.EndPoint.NOTIFI;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.RefreshTokenDevice;
import com.odauday.ui.alert.service.Notification;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by kunsubin on 6/5/2018.
 */

public interface NotificationService {
    
    @POST(NOTIFI+"/send")
    Single<JsonResponse<MessageResponse>> sendNotification(@Body Notification notification);
    
    @POST(NOTIFI +"/save-token")
    Single<JsonResponse<MessageResponse>> saveRefreshTokenDevice(@Body RefreshTokenDevice refreshTokenDevice);
}
