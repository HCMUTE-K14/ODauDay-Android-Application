package com.odauday.data.remote.image;

import static com.odauday.api.EndPoint.IMAGE_UPLOAD;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import io.reactivex.Single;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by infamouSs on 5/1/18.
 */
public interface ImageService {
    
    @Multipart
    @POST(IMAGE_UPLOAD)
    Single<JsonResponse<MessageResponse>> upload(@Part List<MultipartBody.Part> images);
}
