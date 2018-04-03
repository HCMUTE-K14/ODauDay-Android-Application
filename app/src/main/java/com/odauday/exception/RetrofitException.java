package com.odauday.exception;

import java.io.IOException;
import retrofit2.Response;

/**
 * Created by infamouSs on 3/25/18.
 */

public class RetrofitException extends BaseException {

    private final String url;
    private final Response response;
    private final Kind kind;

    RetrofitException(String message, String url, Response response, Kind kind,
        Throwable exception) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
    }

    public static RetrofitException httpError(String url, Response response) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, null);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED,
            exception);
    }

    public String getUrl() {
        return url;
    }

    public Response getResponse() {
        return response;
    }

    public Kind getKind() {
        return kind;
    }

    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }
}