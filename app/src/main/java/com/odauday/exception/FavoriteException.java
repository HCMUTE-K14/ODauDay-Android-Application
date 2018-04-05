package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteException  extends BaseException{
    
    public FavoriteException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public FavoriteException(String message) {
        super(message);
    }
    
    public FavoriteException(String message, Exception ex) {
        super(message, ex);
    }
    
    public FavoriteException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public FavoriteException(Exception ex) {
        super(ex);
    }
}
