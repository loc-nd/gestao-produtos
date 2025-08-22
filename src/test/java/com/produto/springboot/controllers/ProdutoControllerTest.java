package com.produto.springboot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.exception.BusinessException;
import com.produto.springboot.exception.ProdutoNaoEncontradoException;
import com.produto.springboot.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoEntity produtoEntity;

    private ProdutoRecordDTO produtoRecordDTO;

    private UUID produtoId;

    @BeforeEach
    void setUp() {
        produtoId = UUID.randomUUID();

        produtoEntity = new ProdutoEntity();
        produtoEntity.setIdProduto(produtoId);
        produtoEntity.setNome("Livro Testes Unitários");
        produtoEntity.setDescricao("JUnit Tests");
        produtoEntity.setCategoria("Livros");
        produtoEntity.setIsbn("123456");

        produtoRecordDTO = new ProdutoRecordDTO(
                "Livro Testes Unitários",
                BigDecimal.valueOf(109.90),
                "JUnit Tests",
                "Livros",
                "123456"
        );
    }

    @Test
    void salvarProduto_deveSalvarProdutoComRetorno201QuandoCriado() throws Exception {
        when(produtoService.salvar(any(ProdutoRecordDTO.class))).thenReturn(produtoEntity);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRecordDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(produtoRecordDTO.nome()))
                .andExpect(jsonPath("$.isbn").value(produtoRecordDTO.isbn()));
    }

    @Test
    void salvarProduto_deveRetornar400QuandoNomeDuplicado() throws Exception {
        when(produtoService.salvar(any(ProdutoRecordDTO.class))).
                thenThrow(new BusinessException("Já existe um produto com este nome"));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRecordDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um produto com este nome"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void listarProdutos_DeveRetornar200ComListaDeProdutos() throws Exception {
        when(produtoService.listarTodos()).thenReturn(List.of(produtoEntity));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Livro Testes Unitários"))
                .andExpect(jsonPath("$[0].isbn").value("123456"));

    }

    @Test
    void listarProdutosPorId_DeveRetornar200QuandoProdutoExiste() throws Exception {
        when(produtoService.listarPorId(produtoId)).thenReturn(produtoEntity);

        mockMvc.perform(get("/produtos/{id}", produtoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Livro Testes Unitários"));

    }

    @Test
    void listarProdutosPorId_DeveRetornar404QuandoProdutoNaoExiste() throws Exception {
        when(produtoService.listarPorId(produtoId)).
                thenThrow(new ProdutoNaoEncontradoException("Produto nao encontrado"));

        mockMvc.perform(get("/produtos/{id}", produtoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto nao encontrado"))
                .andExpect(jsonPath("$.status").value(404));

    }

    @Test
    void atualizarProduto_DeveRetornar200ComProdutoAtualizado() throws Exception {
        when(produtoService.atualizar(eq(produtoId), any(ProdutoRecordDTO.class))).thenReturn(produtoEntity);

        mockMvc.perform(put("/produtos/{id}", produtoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRecordDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(produtoRecordDTO.nome()));
    }

    @Test
    void deletarProduto_DeveRetornar200ComMensagemDeSucesso() throws Exception {
        Mockito.doNothing().when(produtoService).deletar(eq(produtoId));

        mockMvc.perform(delete("/produtos/{id}", produtoId))
                .andExpect(status().isNoContent());
    }
}