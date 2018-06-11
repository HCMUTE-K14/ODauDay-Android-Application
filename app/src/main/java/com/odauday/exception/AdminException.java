package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class AdminException extends BaseException {
    
    public AdminException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public AdminException(String message) {
        super(message);
    }
    
    public AdminException(String message, Exception ex) {
        super(message, ex);
    }
    
    public AdminException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public AdminException(Exception ex) {
        super(ex);
    }
}
