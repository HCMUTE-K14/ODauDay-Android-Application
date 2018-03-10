package com.odauday.exception;

import com.odauday.utils.Logger;

/**
 * Created by infamouSs on 2/28/18.
 */

public class BaseException extends RuntimeException {
    
    public BaseException(String message) {
        super(message);
        Logger.d(message);
    }
    
    public BaseException(String message, Exception ex) {
        super(message, ex);
        Logger.d(message, ex);
    }
}