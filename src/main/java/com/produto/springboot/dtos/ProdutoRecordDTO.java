package com.produto.springboot.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRecordDTO(
        @Schema(description = "Nome do produto", example = "A Revolução dos Bichos")
        @NotBlank
        String nome,
        @Schema(description = "Valor do produto", example = "29.90")
        @NotNull
        BigDecimal preco,
        @Schema(description = "Descrição detalhada do produto", example = "Verdadeiro clássico moderno, concebido por um dos mais influentes escritores do século XX")
        @NotBlank
        String descricao,
        @Schema(description = "Categoria que o produto faz parte", example = "Livros")
        @NotBlank
        String categoria,
        @Schema(description = "International Standard Book Number - número pra identificar publicações", example = "9788535909555")
        @NotBlank
        String isbn)
{}
