package br.com.merito.project_merito.controller;

import br.com.merito.project_merito.domain.entity.Abastecimento;
import br.com.merito.project_merito.domain.entity.Bomba;
import br.com.merito.project_merito.domain.entity.TipoCombustivel;
import br.com.merito.project_merito.dto.request.AbastecimentoRequest;
import br.com.merito.project_merito.dto.response.AbastecimentoResponse;
import br.com.merito.project_merito.repository.AbastecimentoRepository;
import br.com.merito.project_merito.repository.BombaRepository;
import br.com.merito.project_merito.repository.TipoCombustivelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AbastecimentoControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TipoCombustivelRepository tipoCombustivelRepository;

  @Autowired
  private BombaRepository bombaRepository;

  @Autowired
  private AbastecimentoRepository abastecimentoRepository;

  @Test
  void deveCadastrarAbastecimentoEPersistirRelacionamentoComBomba() throws Exception {
    // Prepara combustível e bomba reais no banco isolado do perfil de teste.
    TipoCombustivel tipoCombustivel = tipoCombustivelRepository.saveAndFlush(
        TipoCombustivel.builder()
            .nome("Gasolina integração")
            .precoPorLitro(new BigDecimal("5.89"))
            .build());
    Bomba bomba = bombaRepository.saveAndFlush(
        Bomba.builder()
            .nome("Bomba integração")
            .tipoCombustivel(tipoCombustivel)
            .build());

    AbastecimentoRequest request = new AbastecimentoRequest(
        bomba.getId(),
        LocalDate.of(2026, 10, 21),
        new BigDecimal("19.63"),
        new BigDecimal("3.333"));

    // Envia o cadastro pelo endpoint HTTP real e confere a resposta serializada.
    MvcResult resultado = mockMvc.perform(post("/api/abastecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.bombaId").value(bomba.getId()))
        .andExpect(jsonPath("$.bombaNome").value("Bomba integração"))
        .andExpect(jsonPath("$.dataAbastecimento").value("21/10/2026"))
        .andExpect(jsonPath("$.valorTotal").value(19.63))
        .andExpect(jsonPath("$.litragem").value(3.333))
        .andReturn();

    AbastecimentoResponse resposta = objectMapper.readValue(
        resultado.getResponse().getContentAsString(),
        AbastecimentoResponse.class);
    assertNotNull(resposta.id());

    // Força a sincronização e confirma o registro e sua bomba associada no banco.
    abastecimentoRepository.flush();
    Abastecimento salvo = abastecimentoRepository.findById(resposta.id())
        .orElseThrow();

    assertTrue(salvo.getId() > 0);
    assertEquals(bomba.getId(), salvo.getBomba().getId());
    assertEquals("Bomba integração", salvo.getBomba().getNome());
    assertEquals(0, new BigDecimal("19.63").compareTo(salvo.getValorTotal()));
    assertEquals(0, new BigDecimal("3.333").compareTo(salvo.getLitragem()));
  }
}
