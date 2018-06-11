package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionException extends BaseException {
    
    public DirectionException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public DirectionException(String message) {
        super(message);
    }
    
    public DirectionException(String message, Exception ex) {
        super(message, ex);
    }
    
    public DirectionException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public DirectionException(Exception ex) {
        super(ex);
    }
}
