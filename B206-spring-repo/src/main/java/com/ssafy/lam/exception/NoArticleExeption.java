package com.ssafy.lam.exception;

public class NoArticleExeption extends IllegalArgumentException{
    private static final long serialVersionUID = 1L;
    public NoArticleExeption(String errorMessage) {
        super(errorMessage);
    }
}
