package com.code4people.jsonrpclib.binding;

public class BindingErrorException extends RuntimeException {

    public BindingErrorException(String message) {
        super(message);
    }

    public BindingErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
