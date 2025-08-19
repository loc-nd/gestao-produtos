package com.produto.springboot.repositories;

import com.produto.springboot.entity.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    private ProdutoEntity criarProduto(){
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome("Livro Testes Unitários");
        produtoEntity.setPreco(BigDecimal.valueOf(109.90));
        produtoEntity.setDescricao("JUnit tests");
        produtoEntity.setCategoria("Livros");
        produtoEntity.setIsbn("1234567891011");
        return produtoEntity;
    };

    @Test
    @DisplayName("Deve retornar true quando já existir um produto com o nome informado")
    void deveRetornarTrueSeExistirProdutoComNome(){
        ProdutoEntity produtoEntity = criarProduto();
        produtoRepository.save(produtoEntity);

        boolean exists = produtoRepository.existsByNome("Livro Testes Unitários");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não existir produto com o nome informado")
    void deveRetornarFalseSeNaoExistirProdutoComNome(){
        boolean exists = produtoRepository.existsByNome("Nome inexistente");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true quando já existir produto com o ISBN informado")
    void deveRetornarTrueSeExistirProdutoComIsbn() {
        ProdutoEntity produtoEntity = criarProduto();
        produtoRepository.save(produtoEntity);

        boolean exists = produtoRepository.existsByIsbn(produtoEntity.getIsbn());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não existir produto com o ISBN informado")
    void deveRetornarFalseSeNaoExistirProdutoComIsbn() {
        boolean exists = produtoRepository.existsByIsbn("9999999999999");

        assertThat(exists).isFalse();
    }

}