package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 4/22/18.
 */
public class AutCompletePlaceException extends BaseException {
    
    public AutCompletePlaceException(
              List<ErrorResponse> errors) {
        super(errors);
    }
    
    public AutCompletePlaceException(String message) {
        super(message);
    }
    
    public AutCompletePlaceException(String message, Exception ex) {
        super(message, ex);
    }
    
    public AutCompletePlaceException(String message, Throwable ex) {
        super(message, ex);
    }
}
