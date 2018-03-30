package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 2/28/18.
 */

public class BaseException extends RuntimeException {
    
    private String message;
    
    private List<ErrorResponse> errors;
    
    public BaseException(List<ErrorResponse> errors) {
        super("List Error");
        this.errors = errors;
    }
    
    public BaseException(String message) {
        super(message);
        this.message = message;
    }
    
    public BaseException(String message, Exception ex) {
        super(message, ex);
        this.message = message;
    }
    
    public BaseException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
    }
    
    
    public BaseException(Exception ex) {
        super(ex);
        this.message = ex.getMessage();
    }
    
    public List<ErrorResponse> getErrors() {
        return errors;
    }
    
    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}