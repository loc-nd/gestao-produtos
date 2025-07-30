package com.produto.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PRODUTOS")
public class ProdutoEntity extends RepresentationModel<ProdutoEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduto;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String categoria;
    @Column(unique = true, nullable = false, length = 13)
    @Size(min = 13, max = 13, message = "O ISBN tem de conter 13 dígitos")
    @Pattern(regexp = "\\d{13}", message = "O ISBN deve conter apenas números")
    private String isbn;

}
