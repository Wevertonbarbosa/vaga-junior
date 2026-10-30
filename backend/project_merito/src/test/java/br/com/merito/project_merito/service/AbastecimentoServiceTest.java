package br.com.merito.project_merito.service;

import br.com.merito.project_merito.domain.entity.Abastecimento;
import br.com.merito.project_merito.domain.entity.Bomba;
import br.com.merito.project_merito.domain.entity.TipoCombustivel;
import br.com.merito.project_merito.dto.request.AbastecimentoRequest;
import br.com.merito.project_merito.dto.response.AbastecimentoResponse;
import br.com.merito.project_merito.exception.BombaNaoEncontradaException;
import br.com.merito.project_merito.exception.RegraNegocioAbastecerException;
import br.com.merito.project_merito.repository.AbastecimentoRepository;
import br.com.merito.project_merito.repository.BombaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TESTE UNITÁRIOS ESPECIFICOS NO SERVIÇO DE ABASTECIMENTO, COM FOCO EM CÁLCULOS DE VALOR TOTAL E LITRAGEM, BEM COMO REGRAS DE NEGÓCIO RELACIONADAS À EXISTÊNCIA DA BOMBA E COMPATIBILIDADE ENTRE VALOR TOTAL E LITRAGEM.
@ExtendWith(MockitoExtension.class)
class AbastecimentoServiceTest {

  @Mock
  private AbastecimentoRepository abastecimentoRepository;

  @Mock
  private BombaRepository bombaRepository;

  @InjectMocks
  private AbastecimentoService abastecimentoService;

  @Test
  @DisplayName("Deve calcular o valor total quando informada somente a litragem")
  void deveCalcularValorTotalQuandoInformadaSomenteLitragem() {
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("6.00"));
    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        null,
        new BigDecimal("10"));

    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));
    when(abastecimentoRepository.save(any(Abastecimento.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    AbastecimentoResponse resposta = abastecimentoService.salvarAbastecimento(request);

    ArgumentCaptor<Abastecimento> abastecimentoCaptor = ArgumentCaptor.forClass(Abastecimento.class);
    verify(abastecimentoRepository).save(abastecimentoCaptor.capture());
    Abastecimento abastecimentoSalvo = abastecimentoCaptor.getValue();

    assertEquals(0, new BigDecimal("60.00").compareTo(resposta.valorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(resposta.litragem()));
    assertEquals(bomba.getId(), resposta.bombaId());
    // compareTo valida os valores da entidade sem exigir uma escala específica.
    assertEquals(0, new BigDecimal("60.00").compareTo(abastecimentoSalvo.getValorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(abastecimentoSalvo.getLitragem()));
    assertSame(bomba, abastecimentoSalvo.getBomba());
  }

  @Test
  @DisplayName("Deve calcular a litragem quando informado somente o valor total")
  void deveCalcularLitragemQuandoInformadoSomenteValorTotal() {
    // A bomba fornece ao serviço o preço por litro usado no cálculo.
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("6.00"));
    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        new BigDecimal("60.00"),
        null);

      // Simula a busca da bomba e faz save devolver a entidade recebida.
    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));
    when(abastecimentoRepository.save(any(Abastecimento.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    AbastecimentoResponse resposta = abastecimentoService.salvarAbastecimento(request);

      // Captura a entidade enviada ao repositório para conferir o estado persistido.
    ArgumentCaptor<Abastecimento> abastecimentoCaptor = ArgumentCaptor.forClass(Abastecimento.class);
    verify(abastecimentoRepository).save(abastecimentoCaptor.capture());
    Abastecimento abastecimentoSalvo = abastecimentoCaptor.getValue();

      // compareTo compara o valor numérico sem exigir a mesma escala decimal.
    assertEquals(0, new BigDecimal("60.00").compareTo(resposta.valorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(resposta.litragem()));
    assertEquals(0, new BigDecimal("60.00").compareTo(abastecimentoSalvo.getValorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(abastecimentoSalvo.getLitragem()));
  }

  @Test
  @DisplayName("Deve aceitar o cadastro quando valor total e litragem são compatíveis")
  void deveAceitarCadastroQuandoValorTotalELitragemSaoCompativeis() {
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("6.00"));
    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        new BigDecimal("60.00"),
        new BigDecimal("10"));

    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));
    when(abastecimentoRepository.save(any(Abastecimento.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    AbastecimentoResponse resposta = abastecimentoService.salvarAbastecimento(request);

    ArgumentCaptor<Abastecimento> abastecimentoCaptor = ArgumentCaptor.forClass(Abastecimento.class);
    verify(abastecimentoRepository).save(abastecimentoCaptor.capture());
    Abastecimento abastecimentoSalvo = abastecimentoCaptor.getValue();

    assertEquals(0, new BigDecimal("60.00").compareTo(resposta.valorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(resposta.litragem()));
    assertEquals(0, new BigDecimal("60.00").compareTo(abastecimentoSalvo.getValorTotal()));
    assertEquals(0, new BigDecimal("10.000").compareTo(abastecimentoSalvo.getLitragem()));
  }

  @Test
  @DisplayName("Deve rejeitar o cadastro quando valor total e litragem são incompatíveis")
  void deveRejeitarCadastroQuandoValorTotalELitragemSaoIncompativeis() {
    
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("6.00"));

    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        new BigDecimal("61.00"),
        new BigDecimal("10"));

    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));

    assertThrows(
        RegraNegocioAbastecerException.class,
        () -> abastecimentoService.salvarAbastecimento(request));
    verify(abastecimentoRepository, never()).save(any(Abastecimento.class));
  }

  @Test
  @DisplayName("Deve rejeitar o cadastro quando valor total e litragem não são informados")
  void deveRejeitarCadastroSemValorTotalESemLitragem() {
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("6.00"));
    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        null,
        null);

    // Bomba válida para que o teste avalie a regra dos valores ausentes.
    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));

    // A regra rejeita o pedido antes de qualquer tentativa de persistência.
    assertThrows(
        RegraNegocioAbastecerException.class,
        () -> abastecimentoService.salvarAbastecimento(request));
    verify(abastecimentoRepository, never()).save(any(Abastecimento.class));
  }

  @Test
  @DisplayName("Deve rejeitar o cadastro quando a bomba não existe")
  void deveRejeitarCadastroQuandoABombaNaoExiste() {
    AbastecimentoRequest request = new AbastecimentoRequest(
        99L,
        LocalDate.of(2026, 10, 15),
        new BigDecimal("60.00"),
        new BigDecimal("10"));

    when(bombaRepository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(
        BombaNaoEncontradaException.class,
        () -> abastecimentoService.salvarAbastecimento(request));
    verify(abastecimentoRepository, never()).save(any(Abastecimento.class));
  }

  @Test
  @DisplayName("Deve arredondar o valor total para duas casas decimais")
  void deveArredondarValorTotalParaDuasCasasDecimais() {
    TipoCombustivel tipoCombustivel = new TipoCombustivel(
        2L,
        "Gasolina",
        new BigDecimal("5.89"));
    Bomba bomba = new Bomba(1L, "Bomba 1", tipoCombustivel);
    AbastecimentoRequest request = new AbastecimentoRequest(
        1L,
        LocalDate.of(2026, 10, 15),
        null,
        new BigDecimal("3.333"));

    // Confirma o arredondamento HALF_UP calculado para a bomba válida.
    when(bombaRepository.findById(1L)).thenReturn(Optional.of(bomba));
    when(abastecimentoRepository.save(any(Abastecimento.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    AbastecimentoResponse resposta = abastecimentoService.salvarAbastecimento(request);

    // Confere tanto a resposta do serviço quanto o valor enviado para persistência.
    ArgumentCaptor<Abastecimento> abastecimentoCaptor = ArgumentCaptor.forClass(Abastecimento.class);
    verify(abastecimentoRepository).save(abastecimentoCaptor.capture());
    assertEquals(0, new BigDecimal("19.63").compareTo(resposta.valorTotal()));
    assertEquals(0, new BigDecimal("19.63").compareTo(abastecimentoCaptor.getValue().getValorTotal()));
  }
}
