package com.odauday.exception;

import com.odauday.data.remote.model.ErrorResponse;
import java.util.List;

/**
 * Created by infamouSs on 3/27/18.
 */

public class RegisterException extends BaseException {

    public RegisterException(List<ErrorResponse> errors) {
        super(errors);
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Exception ex) {
        super(message, ex);
    }

    public RegisterException(String message, Throwable ex) {
        super(message, ex);
    }

    public RegisterException(Exception ex) {
        super(ex);
    }
}
