package com.odauday.data.remote;

import static com.odauday.api.EndPoint.CHANGE_PASSWORD;
import static com.odauday.api.EndPoint.FORGOT_PASSWORD;
import static com.odauday.api.EndPoint.LOGIN_NORMAL;
import static com.odauday.api.EndPoint.LOGIN_WITH_FACEBOOK;
import static com.odauday.api.EndPoint.REGISTER;
import static com.odauday.api.EndPoint.USERS;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.users.ChangePasswordRequest;
import com.odauday.data.remote.model.users.FacebookAuthRequest;
import com.odauday.data.remote.model.users.ForgotPasswordRequest;
import com.odauday.data.remote.model.users.LoginResponse;
import com.odauday.data.remote.model.users.NormalAuthRequest;
import com.odauday.data.remote.model.users.RegisterRequest;
import com.odauday.model.User;
import com.odauday.ui.search.common.SearchCriteria;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
        
        @POST("/health-check")
        Single<JsonResponse<MessageResponse>> test(@Body SearchCriteria searchCriteria);
        
    }
    
    interface Protect {
        
        @PUT(USERS + "/{id}")
        Single<JsonResponse<MessageResponse>> updateProfile(@Path("id") String userId,
            @Body User user);
        
        @PUT(CHANGE_PASSWORD)
        Single<JsonResponse<MessageResponse>> changePassword(@Body ChangePasswordRequest request);
    }
}
