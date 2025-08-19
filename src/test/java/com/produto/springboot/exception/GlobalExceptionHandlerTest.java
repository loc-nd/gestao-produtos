package com.produto.springboot.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void deveRetornarNotFoundQuandoProdutoNaoEncontrado() {
        ProdutoNaoEncontradoException exception = new ProdutoNaoEncontradoException("Produto nao encontrado");

        ResponseEntity<String> response = globalExceptionHandler.handleProdutoNaoEncontrado(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Produto nao encontrado");
    }

    @Test
    void deveRetornarBadRequestQuandoBusinessException() {
        BusinessException exception = new BusinessException("Erro de negócio");

        ResponseEntity<String> response = globalExceptionHandler.handlerBusinessException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Erro de negócio");
    }

    @Test
    void deveRetornarInternalServerErrorQuandoExceptionGenerica(){
        Exception exception = new Exception("Erro inesperado na aplicação");

        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Erro inesperado na aplicação");
    }
}