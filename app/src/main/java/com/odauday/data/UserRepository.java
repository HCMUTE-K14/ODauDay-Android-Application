package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.UserService;
import com.odauday.data.remote.UserService.Protect;
import com.odauday.data.remote.UserService.Public;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.users.AbstractAuthRequest;
import com.odauday.data.remote.model.users.FacebookAuthRequest;
import com.odauday.data.remote.model.users.ForgotPasswordRequest;
import com.odauday.data.remote.model.users.LoginResponse;
import com.odauday.data.remote.model.users.LoginType;
import com.odauday.data.remote.model.users.NormalAuthRequest;
import com.odauday.data.remote.model.users.RegisterRequest;
import com.odauday.exception.ForgotPasswordException;
import com.odauday.exception.LoginException;
import com.odauday.exception.RegisterException;
import com.odauday.model.User;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.utils.JwtUtils;
import com.odauday.utils.JwtUtils.JwtModel;
import io.reactivex.Single;
import javax.inject.Inject;
import timber.log.Timber;

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
    
    public Single<JsonResponse<MessageResponse>> test(SearchCriteria searchCriteria) {
        return mPublicUserService.test(searchCriteria)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<User> login(AbstractAuthRequest request) {
        Single<JsonResponse<LoginResponse>> result;
        if (request.getType() == LoginType.FACEBOOK) {
            result = mPublicUserService.login((FacebookAuthRequest) request);
        } else if (request.getType() == LoginType.NORMAL) {
            result = mPublicUserService.login((NormalAuthRequest) request);
        } else {
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
                        Timber.d(response.getData().toString());
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
        
        return JwtUtils.parseBody(jwtModel, User.class);
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
