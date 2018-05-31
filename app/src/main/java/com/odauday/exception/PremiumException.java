package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 5/31/18.
 */
public class PremiumException extends BaseException {
    
    public PremiumException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public PremiumException(String message) {
        super(message);
    }
    
    public PremiumException(String message, Exception ex) {
        super(message, ex);
    }
    
    public PremiumException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public PremiumException(Exception ex) {
        super(ex);
    }
}
