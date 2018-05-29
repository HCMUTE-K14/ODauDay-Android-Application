package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.user.UserService;
import com.odauday.data.remote.user.UserService.Protect;
import com.odauday.data.remote.user.UserService.Public;
import com.odauday.data.remote.user.model.AbstractAuthRequest;
import com.odauday.data.remote.user.model.FacebookAuthRequest;
import com.odauday.data.remote.user.model.ForgotPasswordRequest;
import com.odauday.data.remote.user.model.LoginResponse;
import com.odauday.data.remote.user.model.NormalAuthRequest;
import com.odauday.data.remote.user.model.RegisterRequest;
import com.odauday.exception.ForgotPasswordException;
import com.odauday.exception.LoginException;
import com.odauday.exception.RegisterException;
import com.odauday.model.User;
import com.odauday.utils.JwtUtils;
import com.odauday.utils.JwtUtils.JwtModel;
import com.odauday.utils.JwtUtils.UserInJWTModel;
import com.odauday.utils.TextUtils;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/27/18.
 */

public class UserRepository implements Repository {
    
    private final UserService.Public mPublicUserService;
    private final UserService.Protect mProtectUserService;
    
    private final PreferencesHelper mPreferencesHelper;
    
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public UserRepository(
        UserService.Public publicUserService,
        UserService.Protect protectUserService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        
        this.mPublicUserService = publicUserService;
        this.mProtectUserService = protectUserService;
        this.mPreferencesHelper = preferencesHelper;
        
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    public boolean isNeedLogin() {
        return TextUtils.isEmpty(mPreferencesHelper.get(PrefKey.CURRENT_USER, ""))
               || TextUtils.isEmpty(mPreferencesHelper.get(PrefKey.USER_ID, ""))
               || TextUtils.isEmpty(mPreferencesHelper.get(PrefKey.ACCESS_TOKEN, ""));
    }
    
    public Single<User> login(AbstractAuthRequest request) {
        Single<JsonResponse<LoginResponse>> result;
        switch (request.getType()) {
            case FACEBOOK:
                result = mPublicUserService.login((FacebookAuthRequest) request);
                break;
            case NORMAL:
                result = mPublicUserService.login((NormalAuthRequest) request);
                break;
            default:
                throw new LoginException("Invalid request");
        }
        
        Single<User> userSingle = result.map(response -> {
            try {
                if (response.isSuccess()) {
                    String accessToken = response.getData().getAccessToken();
                    
                    User userFromJwt = decodeUserAccessToken(accessToken);
                    
                    mPreferencesHelper.put(PrefKey.ACCESS_TOKEN, accessToken);
                    mPreferencesHelper
                        .put(PrefKey.CURRENT_USER, JwtUtils.toJson(userFromJwt, User.class));
                    mPreferencesHelper.put(PrefKey.USER_ID, userFromJwt.getId());
                    
                    return userFromJwt;
                } else {
                    throw new LoginException(response.getErrors());
                }
            } catch (Exception ex) {
                if (ex instanceof LoginException) {
                    throw ex;
                }
                throw new LoginException(ex.getMessage());
            }
        });
        
        return userSingle
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
        
    }
    
    public Single<MessageResponse> register(RegisterRequest request) {
        Single<JsonResponse<MessageResponse>> result = mPublicUserService.register(request);
        
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    } else {
                        throw new RegisterException(response.getErrors());
                    }
                } catch (Exception ex) {
                    if (ex instanceof RegisterException) {
                        throw ex;
                    }
                    throw new RegisterException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> forgotPassword(ForgotPasswordRequest request) {
        Single<JsonResponse<MessageResponse>> result = mPublicUserService.forgotPassword(request);
        
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    } else {
                        throw new ForgotPasswordException(response.getErrors());
                    }
                } catch (Exception ex) {
                    if (ex instanceof ForgotPasswordException) {
                        throw ex;
                    }
                    throw new RegisterException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    private User decodeUserAccessToken(String accessToken) throws Exception {
        JwtModel jwtModel = JwtUtils.decoded(accessToken);
        UserInJWTModel model = JwtUtils.parseBody(jwtModel, UserInJWTModel.class);
        
        return model.getUser();
    }
    
    
    public Public getPublicUserService() {
        return mPublicUserService;
    }
    
    public Protect getProtectUserService() {
        return mProtectUserService;
    }
    
    public SchedulersExecutor scheduler() {
        return mSchedulersExecutor;
    }
}
