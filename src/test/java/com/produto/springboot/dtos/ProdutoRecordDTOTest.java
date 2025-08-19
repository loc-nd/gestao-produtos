package com.produto.springboot.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoRecordDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarQuandoForValido(){
        ProdutoRecordDTO dto = new ProdutoRecordDTO(
                "Livro JUnit Test",
                BigDecimal.valueOf(59.90),
                "JUnit Tests",
                "Livros",
                "1234567891011"
        );
    }
    @Test
    void deveFalharQuandoNomeVazio() {
        ProdutoRecordDTO produtoRecordDTO = new ProdutoRecordDTO(
                "",
                BigDecimal.valueOf(59.90),
                "Descrição teste",
                "Categoria de teste",
                "123456"
        );
    }

    @Test
    void deveFalharQuandoPrecoForNulo(){
        ProdutoRecordDTO dto = new ProdutoRecordDTO(
               "Livro JUnit Test",
               null,
               "JUnit Tests",
               "Livros",
               "1234567891011"
        );

        Set<ConstraintViolation<ProdutoRecordDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty().hasSize(1);

        ConstraintViolation<ProdutoRecordDTO> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("preco");
        assertThat(violation.getMessage()).isEqualTo("não deve ser nulo");
    }

}