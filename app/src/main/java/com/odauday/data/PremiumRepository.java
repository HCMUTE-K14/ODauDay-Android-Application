package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.premium.PremiumService;
import com.odauday.data.remote.premium.model.SubscribeRequest;
import com.odauday.exception.PremiumException;
import com.odauday.exception.PropertyException;
import com.odauday.model.Premium;
import com.odauday.utils.TextUtils;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 5/31/18.
 */
public class PremiumRepository implements Repository {
    
    private final PremiumService mPremiumService;
    private final PreferencesHelper mPreferencesHelper;
    private final SchedulersExecutor mSchedulersExecutor;
    
    public PremiumRepository(PremiumService premiumService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        this.mPremiumService = premiumService;
        this.mPreferencesHelper = preferencesHelper;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<List<Premium>> get() {
        return mPremiumService
            .get()
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new PremiumException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof PropertyException) {
                        throw ex;
                    }
                    throw new PremiumException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> subscribe(SubscribeRequest request) {
        if (TextUtils.isEmpty(request.getUserId())) {
            String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
            request.setUserId(userId);
        }
        
        return mPremiumService
            .subscribe(request)
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new PremiumException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof PropertyException) {
                        throw ex;
                    }
                    throw new PremiumException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
