package com.odauday.exception;

/**
 * Created by infamouSs on 4/9/18.
 */

public class TagException extends BaseException {
    
    public TagException(String message) {
        super(message);
    }
    
    public TagException(String message, Exception ex) {
        super(message, ex);
    }
    
    public TagException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public TagException(Exception ex) {
        super(ex);
    }
}
