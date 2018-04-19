package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class PropertyException extends BaseException {
    
    public PropertyException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public PropertyException(String message) {
        super(message);
    }
    
    public PropertyException(String message, Exception ex) {
        super(message, ex);
    }
    
    public PropertyException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public PropertyException(Exception ex) {
        super(ex);
    }
}
