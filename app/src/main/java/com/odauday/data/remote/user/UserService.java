package com.odauday.data.remote.user;

import static com.odauday.api.EndPoint.CHANGE_PASSWORD;
import static com.odauday.api.EndPoint.FORGOT_PASSWORD;
import static com.odauday.api.EndPoint.GET_AMOUNT;
import static com.odauday.api.EndPoint.LOGIN_NORMAL;
import static com.odauday.api.EndPoint.LOGIN_WITH_FACEBOOK;
import static com.odauday.api.EndPoint.REGISTER;
import static com.odauday.api.EndPoint.RESEND_ACTIVATION;
import static com.odauday.api.EndPoint.USERS;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.user.model.ChangePasswordRequest;
import com.odauday.data.remote.user.model.FacebookAuthRequest;
import com.odauday.data.remote.user.model.ForgotPasswordRequest;
import com.odauday.data.remote.user.model.LoginResponse;
import com.odauday.data.remote.user.model.NormalAuthRequest;
import com.odauday.data.remote.user.model.RegisterRequest;
import com.odauday.model.User;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 2/27/18.
 */

public interface UserService {
    
    interface Public {
        
        @POST(LOGIN_NORMAL)
        Single<JsonResponse<LoginResponse>> login(@Body NormalAuthRequest request);
        
        @POST(LOGIN_WITH_FACEBOOK)
        Single<JsonResponse<LoginResponse>> login(@Body FacebookAuthRequest request);
        
        @POST(REGISTER)
        Single<JsonResponse<MessageResponse>> register(@Body RegisterRequest request);
        
        @POST(FORGOT_PASSWORD)
        Single<JsonResponse<MessageResponse>> forgotPassword(@Body ForgotPasswordRequest request);
        
        @POST(RESEND_ACTIVATION)
        Single<JsonResponse<MessageResponse>> reSendActivation(@Query("email") String email);
        
    }
    
    interface Protect {
        
        @PUT(USERS + "/{id}")
        Single<JsonResponse<MessageResponse>> updateProfile(@Path("id") String userId,
            @Body User user);
        
        @POST(CHANGE_PASSWORD)
        Single<JsonResponse<MessageResponse>> changePassword(@Body ChangePasswordRequest request);
        
        @GET(GET_AMOUNT + "/{id}")
        Single<JsonResponse<Long>> getAmount(@Path("id") String userId);
        
    }
}
