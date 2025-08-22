package com.produto.springboot.controllers;

import com.produto.springboot.dtos.ProdutoRecordDTO;
import com.produto.springboot.entity.ProdutoEntity;
import com.produto.springboot.exception.ErrorResponse;
import com.produto.springboot.repositories.ProdutoRepository;
import com.produto.springboot.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cria um novo produto.",
            description = "Adiciona um novo produto à base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inserção de produto realizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProdutoEntity.class))),
            @ApiResponse(responseCode = "400", description = "Erro na inserção do produto.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Já existe um produto com este nome\", \"status\": 400, \"timestamp\": \"2025-08-22T13:10:00\" }"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Erro inesperado na aplicação\", \"status\": 500, \"timestamp\": \"2025-08-22T13:10:00\" }")))
    })
    @PostMapping("/produtos")
    public ResponseEntity<ProdutoEntity> salvarProduto(@RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoRecordDTO));
    }





    @Operation(summary = "Lista todos os produtos.",
            description = "Realiza uma listagem de todos os produtos da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoEntity.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Erro inesperado na aplicação\", \"status\": 500, \"timestamp\": \"2025-08-22T13:10:00\" }")))
    })
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoEntity>> listarProdutos() {
        List<ProdutoEntity> produtos = produtoService.listarTodos();

        produtos.forEach(produto -> produto
                .add(linkTo(methodOn(ProdutoController.class)
                        .listarProdutoPorId(produto.getIdProduto())).withSelfRel()));
        return ResponseEntity.ok(produtos);
    }




    @Operation(summary = "Lista produtos por id",
            description = "Realiza uma busca por id de um produto específico da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProdutoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Produto não encontrado\", \"status\": 404, \"timestamp\": \"2025-08-22T13:10:00\" }"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Erro inesperado na aplicação\", \"status\": 500, \"timestamp\": \"2025-08-22T13:10:00\" }")))
    })
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }





    @Operation(summary = "Atualiza produtos",
            description = "Realiza a atualização de um produto específico da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProdutoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Produto não encontrado\", \"status\": 404, \"timestamp\": \"2025-08-22T13:10:00\" }"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Erro inesperado na aplicação\", \"status\": 500, \"timestamp\": \"2025-08-22T13:10:00\" }")))
    })
    @PutMapping("/produtos/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable(value = "id") UUID id,
                                                   @RequestBody @Valid ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoRecordDTO));
    }





    @Operation(summary = "Deleta produtos",
            description = "Realiza a deleção de um produto específico da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Produto não encontrado\", \"status\": 404, \"timestamp\": \"2025-08-22T13:10:00\" }"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"message\": \"Erro inesperado na aplicação\", \"status\": 500, \"timestamp\": \"2025-08-22T13:10:00\" }")))
    })
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "id") UUID id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
