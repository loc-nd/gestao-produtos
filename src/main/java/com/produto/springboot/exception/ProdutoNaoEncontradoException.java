package com.produto.springboot.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
