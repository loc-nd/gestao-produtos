package com.produto.springboot.integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produto.springboot.dtos.ProdutoRecordDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarEListarProduto() throws Exception {
        ProdutoRecordDTO produtoRecordDTO = new ProdutoRecordDTO(
                "Livro Teste de Integração",
                BigDecimal.valueOf(99.90),
                "Teste de Integração",
                "Livros",
                "1234567891011"
        );

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRecordDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProduto").exists())
                .andExpect(jsonPath("$.nome").value("Livro Teste de Integração"));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Livro Teste de Integração"));

    }
}
