package com.produto.springboot.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRecordDTO(@NotBlank String nome, @NotNull BigDecimal preco, @NotBlank String descricao, @NotBlank String categoria, @NotBlank String isbn) {
}
