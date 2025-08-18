package com.produto.springboot.service;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.exception.BusinessException;
import com.produto.springboot.exception.ProdutoNaoEncontradoException;
import com.produto.springboot.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    private ProdutoEntity produtoEntity;

    private ProdutoRecordDTO produtoRecordDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        produtoEntity = new ProdutoEntity();
        ReflectionTestUtils.setField(produtoEntity, "idProduto", UUID.randomUUID());
        produtoEntity.setNome("Livro");
        produtoEntity.setPreco(BigDecimal.valueOf(109.90));
        produtoEntity.setDescricao("Junit tests");
        produtoEntity.setCategoria("Livros");
        produtoEntity.setIsbn("12345");

        produtoRecordDTO = new ProdutoRecordDTO(
                "Livro",
                BigDecimal.valueOf(109.90),
                "Junit tests",
                "Livros",
                "12345"
        );
    }

    @Test
    void deveSalvarProdutoComSucesso() {

        when(produtoRepository.existsByNome(produtoRecordDTO.nome())).thenReturn(false);
        when(produtoRepository.existsByIsbn(produtoRecordDTO.isbn())).thenReturn(false);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ProdutoEntity saved = produtoService.salvar(produtoRecordDTO);

        assertNotNull(saved);
        assertEquals(produtoRecordDTO.nome(), saved.getNome());
        assertEquals(produtoRecordDTO.isbn(), saved.getIsbn());
        assertEquals(produtoRecordDTO.preco(), saved.getPreco());
        assertEquals(produtoRecordDTO.descricao(), saved.getDescricao());
        assertEquals(produtoRecordDTO.categoria(), saved.getCategoria());
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeJaExiste() {
        when(produtoRepository.existsByNome(produtoRecordDTO.nome())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> produtoService.salvar(produtoRecordDTO));

        assertTrue(exception.getMessage().contains(produtoRecordDTO.nome()));
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveRetornarListaDeProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produtoEntity));

        List<ProdutoEntity> produtos = produtoService.listarTodos();

        assertEquals(1, produtos.size());
        assertEquals("Livro", produtos.get(0).getNome());
    }

    @Test
    void deveRetornarProdutoQuandoExiste(){
        when(produtoRepository.findById(produtoEntity.getIdProduto())).thenReturn(Optional.of(produtoEntity));

        ProdutoEntity produtoEncontrado = produtoService.listarPorId(produtoEntity.getIdProduto());

        assertNotNull(produtoEncontrado);
        assertEquals(produtoEntity.getIdProduto(), produtoEncontrado.getIdProduto());
    }

    @Test
    void listarPorId_DeveLancarExcecaoQuandoNaoExiste() {
        UUID idInexistente = UUID.randomUUID();
        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.listarPorId(idInexistente));

    }

    @Test
    void deveAtualizarProduto(){
        UUID id = UUID.randomUUID();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ProdutoEntity produtoAtualizado = produtoService.listarPorId(id);

        assertNotNull(produtoAtualizado);
        assertEquals(produtoRecordDTO.nome(), produtoAtualizado.getNome());
        assertEquals(produtoRecordDTO.isbn(), produtoAtualizado.getIsbn());
        assertEquals(produtoRecordDTO.preco(), produtoAtualizado.getPreco());
        assertEquals(produtoRecordDTO.descricao(), produtoAtualizado.getDescricao());
        assertEquals(produtoRecordDTO.categoria(), produtoAtualizado.getCategoria());
    }

    @Test
    void deveExcluirPorId(){
        UUID id = UUID.randomUUID();

        produtoService.deletar(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }
}