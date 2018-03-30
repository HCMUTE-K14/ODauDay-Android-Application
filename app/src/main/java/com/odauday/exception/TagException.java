package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 3/30/2018.
 */

public class TagException extends BaseException {
    
    public TagException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public TagException(String message) {
        super(message);
    }
    
    public TagException(String message, Exception ex) {
        super(message, ex);
    }
    
    public TagException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public TagException(Exception ex) {
        super(ex);
    }
}
