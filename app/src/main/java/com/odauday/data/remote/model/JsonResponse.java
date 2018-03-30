package com.odauday.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by infamouSs on 3/23/18.
 */

public class JsonResponse<DATA> implements BaseResponse {
    
    @SerializedName("data")
    @Expose
    private DATA data;
    
    @SerializedName("errors")
    @Expose
    private List<ErrorResponse> errors;
    
    @SerializedName("datetime")
    @Expose
    private Date datetime;
    
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    
    @SerializedName("code")
    @Expose
    private int code;
    
    @SerializedName("status_text")
    @Expose
    private String statusText;
    
    @SerializedName("success")
    @Expose
    private boolean success;
    
    public JsonResponse() {
    }
    
    
    public Date getDatetime() {
        return datetime;
    }
    
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getStatusText() {
        return statusText;
    }
    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    public boolean getSuccess() {
        return success;
    }
    
    public DATA getData() {
        return data;
    }
    
    public void setData(DATA data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public List<ErrorResponse> getErrors() {
        return errors;
    }
    
    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }
    
    
    public boolean isHasErrorList() {
        return this.errors == null || this.errors.isEmpty();
    }
    
    @Override
    public String toString() {
        return "JsonResponse{" +
               "datetime='" + datetime + '\'' +
               ", timestamp=" + timestamp +
               ", code=" + code +
               ", statusText='" + statusText + '\'' +
               ", success=" + success +
               ", data=" + data +
               '}';
    }
}
