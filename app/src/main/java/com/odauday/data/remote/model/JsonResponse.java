package com.odauday.data.remote.model;

/**
 * Created by infamouSs on 2/28/18.
 */

public class JsonResponse<T> {
    
    private long timestamp;
    private int code;
    private String status_text;
    private String errorMessage;
    private T data;
}
