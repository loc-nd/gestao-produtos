package com.produto.springboot.controllers;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.repositories.ProdutoRepository;
import com.produto.springboot.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;


    @PostMapping("/produtos")
    public ResponseEntity<ProdutoEntity> salvarProduto(@RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoRecordDTO));
    }


    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoEntity>> listarProdutos() {
        List<ProdutoEntity> produtos = produtoService.listarTodos();

        produtos.forEach(produto -> produto
                .add(linkTo(methodOn(ProdutoController.class)
                        .listarProdutoPorId(produto.getIdProduto())).withSelfRel()));
        return ResponseEntity.ok(produtos);
    }


    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }


    @PutMapping("/produtos/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable(value = "id") UUID id,
                                                   @RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoRecordDTO));

    }


    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "id") UUID id) {
        produtoService.deletar(id);
        return ResponseEntity.ok("Produto deletado com sucesso");
    }

}
