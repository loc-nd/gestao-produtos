package com.produto.springboot.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
