package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 4/25/18.
 */
public class GeoInfoException extends BaseException {
    
    public GeoInfoException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public GeoInfoException(String message) {
        super(message);
    }
    
    public GeoInfoException(String message, Exception ex) {
        super(message, ex);
    }
    
    public GeoInfoException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public GeoInfoException(Exception ex) {
        super(ex);
    }
}
