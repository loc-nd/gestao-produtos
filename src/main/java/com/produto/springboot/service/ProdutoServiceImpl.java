package com.produto.springboot.service;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.exception.BusinessException;
import com.produto.springboot.exception.ProdutoNaoEncontradoException;
import com.produto.springboot.repositories.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService{

    @Autowired
    public ProdutoRepository produtoRepository;

    @Override
    public ProdutoEntity salvar(ProdutoRecordDTO produtoRecordDTO) {

        if(produtoRepository.existsByNome(produtoRecordDTO.nome())){
            throw new BusinessException("Já existe um produto com o nome " + produtoRecordDTO.nome());
        }

        if(produtoRepository.existsByIsbn(produtoRecordDTO.isbn())){
            throw new BusinessException("Já existe um produto com o isbn " + produtoRecordDTO.isbn());
        }

        ProdutoEntity produtoEntity = new ProdutoEntity();
        BeanUtils.copyProperties(produtoRecordDTO, produtoEntity);
        return produtoRepository.save(produtoEntity);
    }

    @Override
    public List<ProdutoEntity> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public ProdutoEntity listarPorId(UUID id) {
        return produtoRepository.findById(id).
                orElseThrow(() -> new ProdutoNaoEncontradoException("Produto do ID " + id + " não encontrado"));
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
