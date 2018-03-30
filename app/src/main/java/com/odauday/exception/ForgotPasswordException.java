package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 3/29/18.
 */

public class ForgotPasswordException extends BaseException {
    
    public ForgotPasswordException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public ForgotPasswordException(String message) {
        super(message);
    }
    
    public ForgotPasswordException(String message, Exception ex) {
        super(message, ex);
    }
    
    public ForgotPasswordException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public ForgotPasswordException(Exception ex) {
        super(ex);
    }
}
