package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 5/17/18.
 */
public class NoteException extends BaseException {
    
    public NoteException(List<ErrorResponse> errors) {
        super(errors);
    }
    
    public NoteException(String message) {
        super(message);
    }
    
    public NoteException(String message, Exception ex) {
        super(message, ex);
    }
    
    public NoteException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public NoteException(Exception ex) {
        super(ex);
    }
}
