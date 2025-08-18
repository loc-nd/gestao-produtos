package com.produto.springboot.service;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoService {

    ProdutoEntity salvar(ProdutoRecordDTO produtoRecordDTO);

    List<ProdutoEntity> listarTodos();

    ProdutoEntity listarPorId(UUID id);


    ProdutoEntity atualizar(UUID uuid, ProdutoRecordDTO produtoRecordDTO);

    void deletar(UUID id);
}
