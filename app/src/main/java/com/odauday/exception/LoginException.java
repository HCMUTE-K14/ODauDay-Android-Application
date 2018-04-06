package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 3/23/18.
 */

public class LoginException extends BaseException {
    
    
    public LoginException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public LoginException(String message) {
        super(message);
    }
    
    public LoginException(Exception ex) {
        super(ex);
    }
    
    public LoginException(String message, Exception ex) {
        super(message, ex);
    }
    
    public LoginException(String message, Throwable ex) {
        super(message, ex);
    }
}
