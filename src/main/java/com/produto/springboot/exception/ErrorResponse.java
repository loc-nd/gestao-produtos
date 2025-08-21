package com.produto.springboot.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class ErrorResponse {

    @Schema(description = "Mensagem de erro", example = "Produto não encontrado")
    private String message;

    @Schema(description = "Código de status HTTP", example = "404")
    private int status;

    @Schema(description = "Data e hora do erro", example = "2025-08-21T12:13:00")
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
