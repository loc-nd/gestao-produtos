package com.produto.springboot.controllers;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.repositories.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;



    @PostMapping("/produtos")
    public ResponseEntity<ProdutoEntity> salvarProduto(@RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        BeanUtils.copyProperties(produtoRecordDTO, produtoEntity);
        return new ResponseEntity<>(produtoRepository.save(produtoEntity), HttpStatus.CREATED);
    }


    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoEntity>> listarProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }


    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable(value="id") UUID id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);
        if(produtoEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoEntity.get());
    }


    @PutMapping("/produtos/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable(value="id") UUID id,
                                                   @RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        Optional<ProdutoEntity> produtoEntity0 = produtoRepository.findById(id);
        if(produtoEntity0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        var produtoEntity = produtoEntity0.get();
        BeanUtils.copyProperties(produtoRecordDTO, produtoEntity);
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoEntity));

    }


    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value="id") UUID id) {
        Optional<ProdutoEntity> produtoEntity0 = produtoRepository.findById(id);
        if(produtoEntity0.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }

}
