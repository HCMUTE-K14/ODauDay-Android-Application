package com.odauday.api;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 5/1/18.
 */
public class RequestBodyHelper {
    
    public static RequestBody createRequestBodyFromString(String str) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), str);
    }
    
}
