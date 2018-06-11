package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 6/4/2018.
 */

public class NotifiException extends BaseException {
    
    public NotifiException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public NotifiException(String message) {
        super(message);
    }
    
    public NotifiException(String message, Exception ex) {
        super(message, ex);
    }
    
    public NotifiException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public NotifiException(Exception ex) {
        super(ex);
    }
}
