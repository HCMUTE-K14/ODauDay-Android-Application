package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 4/27/2018.
 */

public class HistoryException extends BaseException {
    
    public HistoryException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public HistoryException(String message) {
        super(message);
    }
    
    public HistoryException(String message, Exception ex) {
        super(message, ex);
    }
    
    public HistoryException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public HistoryException(Exception ex) {
        super(ex);
    }
}
