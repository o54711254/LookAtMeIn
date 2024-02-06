package com.ssafy.lam.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UnAuthorizedException(String errorMessage) {

        super(errorMessage);
    }
}

