package com.produto.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<String> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handlerBusinessException(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro inesperado" + exception.getMessage());
    }
}
