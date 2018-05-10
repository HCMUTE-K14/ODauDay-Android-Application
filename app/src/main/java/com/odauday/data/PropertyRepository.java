package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.image.ImageService;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.property.PropertyService;
import com.odauday.data.remote.property.model.CreatePropertyRequest;
import com.odauday.exception.PropertyException;
import com.odauday.model.Property;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import okhttp3.MultipartBody;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class PropertyRepository implements Repository {
    
    private final PropertyService mProtectPropertyService;
    private final ImageService mImageService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public PropertyRepository(PropertyService protectPropertyService,
        ImageService imageService,
        SchedulersExecutor schedulersExecutor) {
        mProtectPropertyService = protectPropertyService;
        mImageService = imageService;
        mSchedulersExecutor = schedulersExecutor;
    }
    
    
    public Single uploadImage(List<MultipartBody.Part> images) {
        return mImageService.upload(images)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Flowable<MessageResponse> create(CreatePropertyRequest request) {
        
        return Single.merge(
            mProtectPropertyService.create(request.getProperty())
                .subscribeOn(mSchedulersExecutor.io())
                .observeOn(mSchedulersExecutor.ui()),
            mImageService.upload(request.getImages())
                .subscribeOn(mSchedulersExecutor.io())
                .observeOn(mSchedulersExecutor.ui())
        ).map(JsonResponse::getData);
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
    public Single<MessageResponse> changeStatus(String property_id,String status) {
        Single<JsonResponse<MessageResponse>> result = mProtectPropertyService
            .changeStatus(property_id, status);
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
