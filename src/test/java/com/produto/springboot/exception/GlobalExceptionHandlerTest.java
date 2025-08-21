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

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleProdutoNaoEncontrado(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Produto nao encontrado");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    void deveRetornarBadRequestQuandoBusinessException() {
        BusinessException exception = new BusinessException("Erro de negócio");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handlerBusinessException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Erro de negócio");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarInternalServerErrorQuandoExceptionGenerica(){
        Exception exception = new Exception("Erro inesperado na aplicação");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Erro inesperado na aplicação");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}