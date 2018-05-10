package com.odauday.ui.addeditproperty;

import static com.odauday.config.Constants.Task.TASK_CREATE_PROPERTY;

import com.google.gson.Gson;
import com.odauday.data.PropertyRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.property.model.CreatePropertyRequest;
import com.odauday.model.Image;
import com.odauday.model.MyProperty;
import com.odauday.utils.TextUtils;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 4/24/18.
 */
public class AddEditPropertyViewModel extends BaseViewModel {
    
    private final PropertyRepository mPropertyRepository;
    private final PreferencesHelper mPreferencesHelper;
    
    private final Gson mGson;
    
    @Inject
    public AddEditPropertyViewModel(PropertyRepository propertyRepository,
        PreferencesHelper preferencesHelper, Gson gson) {
        this.mPropertyRepository = propertyRepository;
        this.mPreferencesHelper = preferencesHelper;
        this.mGson = gson;
    }
    
    
    public void create(MyProperty property) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        //        if (TextUtils.isEmpty(userId)) {
        //            response.setValue(Resource.error(Task.TASK_CREATE_PROPERTY, null));
        //            return;
        //        }
        
        property.setUserIdCreated(userId);
        
        List<Part> requestImage = new ArrayList<>();
        if (property.getImages() != null && !property.getImages().isEmpty()) {
            for (Image img : property.getImages()) {
                File image = new File(img.getUrl());
                if (!image.exists()) {
                    continue;
                }
                RequestBody requestBodyFile = RequestBody
                    .create(MediaType.parse("multipart/form-data"), image);
                String fileName = System.currentTimeMillis() + "_" + image.getName();
                MultipartBody.Part multipartFile =
                    MultipartBody.Part.createFormData("images", fileName, requestBodyFile);
                img.setUrl(TextUtils.buildImageUrl(fileName));
                
                requestImage.add(multipartFile);
            }
        }
        property.setStatus(MyProperty.PENDING);
        CreatePropertyRequest request = new CreatePropertyRequest(property, requestImage);
        
        Disposable disposable = mPropertyRepository.create(request)
            .doOnSubscribe(onSubscribe -> response.setValue(Resource.loading(null)))
            .subscribe(success -> {
                response.setValue(Resource.success(TASK_CREATE_PROPERTY, ""));
            }, error -> {
                response.setValue(Resource.error(TASK_CREATE_PROPERTY, error));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
}
