package com.odauday.exception;

/**
 * Created by infamouSs on 2/27/18.
 */

public class NetworkException extends BaseException {
    
    public NetworkException(String message) {
        super(message);
    }
    
    public NetworkException(String message, Exception ex) {
        super(message, ex);
    }
}
