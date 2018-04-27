package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.PropertyService;
import com.odauday.data.remote.PropertyService.Protect;
import com.odauday.data.remote.PropertyService.Public;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.PropertyException;
import com.odauday.model.Property;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class PropertyRepository implements Repository {
    
    private final PropertyService.Public mPublicPropertyService;
    private final PropertyService.Protect mProtectPropertyService;
    ;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public PropertyRepository(Public publicPropertyService,
        Protect protectPropertyService,
        SchedulersExecutor schedulersExecutor) {
        mPublicPropertyService = publicPropertyService;
        mProtectPropertyService = protectPropertyService;
        mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<List<Property>> getPropertyOfUser(String user_id) {
        Single<JsonResponse<List<Property>>> result = mProtectPropertyService
            .getPropertyOfUser(user_id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new PropertyException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof PropertyException) {
                        throw ex;
                    }
                    throw new PropertyException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> deleteProperty(String property_id) {
        Single<JsonResponse<MessageResponse>> result = mProtectPropertyService
            .deleteProperty(property_id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new PropertyException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof PropertyException) {
                        throw ex;
                    }
                    throw new PropertyException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
