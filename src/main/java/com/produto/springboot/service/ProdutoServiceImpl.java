package com.produto.springboot.service;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.repositories.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService{

    @Autowired
    public ProdutoRepository produtoRepository;

    @Override
    public ProdutoEntity salvar(ProdutoRecordDTO produtoRecordDTO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        BeanUtils.copyProperties(produtoRecordDTO, produtoEntity);
        return produtoRepository.save(produtoEntity);
    }

    @Override
    public List<ProdutoEntity> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Optional<ProdutoEntity> listarPorId(UUID id) {
        return produtoRepository.findById(id);
    }

    @Override
    public ProdutoEntity atualizar(UUID uuid, ProdutoRecordDTO produtoRecordDTO) {
        ProdutoEntity produtoEntity = produtoRepository.findById(uuid).orElseThrow();
        BeanUtils.copyProperties(produtoRecordDTO, produtoEntity);
        return produtoRepository.save(produtoEntity);
    }

    @Override
    public void deletar(UUID id) {
        produtoRepository.deleteById(id);
    }
}
