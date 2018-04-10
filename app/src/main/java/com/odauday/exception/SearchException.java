package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 4/9/2018.
 */

public class SearchException extends BaseException {
    
    public SearchException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public SearchException(String message) {
        super(message);
    }
    
    public SearchException(String message, Exception ex) {
        super(message, ex);
    }
    
    public SearchException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public SearchException(Exception ex) {
        super(ex);
    }
}
