package br.com.merito.project_merito.service;

import br.com.merito.project_merito.domain.entity.Abastecimento;
import br.com.merito.project_merito.domain.entity.Bomba;
import br.com.merito.project_merito.dto.request.AbastecimentoRequest;
import br.com.merito.project_merito.dto.response.AbastecimentoResponse;
import br.com.merito.project_merito.exception.AbastecimentoNaoEncontradoException;
import br.com.merito.project_merito.exception.BombaNaoEncontradaException;
import br.com.merito.project_merito.exception.RegraNegocioAbastecerException;
import br.com.merito.project_merito.repository.AbastecimentoRepository;
import br.com.merito.project_merito.repository.BombaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class AbastecimentoService {

  private final AbastecimentoRepository abastecimentoRepository;
  private final BombaRepository bombaRepository;

  public AbastecimentoService(
      AbastecimentoRepository abastecimentoRepository,
      BombaRepository bombaRepository) {
    this.abastecimentoRepository = abastecimentoRepository;
    this.bombaRepository = bombaRepository;
  }

  public AbastecimentoResponse salvarAbastecimento(
      AbastecimentoRequest abastecimentoRequest) {
    Bomba bomba = buscarBomba(abastecimentoRequest.bombaId());

    BigDecimal precoPorLitro = bomba.getTipoCombustivel().getPrecoPorLitro();

    BigDecimal[] valores = calcularValoresAbastecimento(
        abastecimentoRequest.valorTotal(),
        abastecimentoRequest.litragem(),
        precoPorLitro);

    BigDecimal valorTotal = valores[0];
    BigDecimal litragem = valores[1];

    Abastecimento abastecimento = new Abastecimento();

    BeanUtils.copyProperties(
        abastecimentoRequest,
        abastecimento);

    abastecimento.setBomba(bomba);
    abastecimento.setValorTotal(valorTotal);
    abastecimento.setLitragem(litragem);

    Abastecimento abastecimentoSalvo = abastecimentoRepository.save(abastecimento);

    return converterParaResponse(abastecimentoSalvo);
  }

  public AbastecimentoResponse buscarPorId(Long id) {

    Optional<Abastecimento> abastecimentoOptional = abastecimentoRepository.findById(id);

    if (abastecimentoOptional.isPresent()) {

      return converterParaResponse(
          abastecimentoOptional.get());

    } else {
      throw new AbastecimentoNaoEncontradoException(
          "Abastecimento não encontrado no banco de dados!");
    }
  }

  public List<AbastecimentoResponse> listarTodos() {

    return abastecimentoRepository
        .findAll()
        .stream()
        .map(this::converterParaResponse)
        .toList();
  }

  public void excluir(Long id) {

    Optional<Abastecimento> abastecimentoOptional = abastecimentoRepository.findById(id);

    if (abastecimentoOptional.isPresent()) {

      abastecimentoRepository.delete(
          abastecimentoOptional.get());

    } else {
      throw new AbastecimentoNaoEncontradoException(
          "Abastecimento não encontrado para excluir!");
    }
  }

  public AbastecimentoResponse atualizar(
      Long id,
      AbastecimentoRequest abastecimentoRequest) {
    Optional<Abastecimento> abastecimentoOptional = abastecimentoRepository.findById(id);

    if (abastecimentoOptional.isEmpty()) {
      throw new AbastecimentoNaoEncontradoException(
          "Abastecimento não encontrado para atualizar!");
    }

    Abastecimento abastecimento = abastecimentoOptional.get();

    Bomba bomba = buscarBomba(abastecimentoRequest.bombaId());

    BigDecimal precoPorLitro = bomba.getTipoCombustivel().getPrecoPorLitro();

    BigDecimal[] valores = calcularValoresAbastecimento(
        abastecimentoRequest.valorTotal(),
        abastecimentoRequest.litragem(),
        precoPorLitro);

    BigDecimal valorTotal = valores[0];
    BigDecimal litragem = valores[1];

    BeanUtils.copyProperties(
        abastecimentoRequest,
        abastecimento);

    abastecimento.setBomba(bomba);
    abastecimento.setValorTotal(valorTotal);
    abastecimento.setLitragem(litragem);

    Abastecimento abastecimentoAtualizado = abastecimentoRepository.save(abastecimento);

    return converterParaResponse(abastecimentoAtualizado);
  }

  private Bomba buscarBomba(Long bombaId) {

    Optional<Bomba> bombaOptional = bombaRepository.findById(bombaId);

    if (bombaOptional.isPresent()) {

      return bombaOptional.get();

    } else {
      throw new BombaNaoEncontradaException(
          "Bomba não encontrada no banco de dados!");
    }
  }

  private AbastecimentoResponse converterParaResponse(
      Abastecimento abastecimento) {

    return new AbastecimentoResponse(
        abastecimento.getId(),
        abastecimento.getBomba().getId(),
        abastecimento.getBomba().getNome(),
        abastecimento.getDataAbastecimento(),
        abastecimento.getValorTotal(),
        abastecimento.getLitragem());
  }

  private BigDecimal calcularValorTotal(
      BigDecimal litragem,
      BigDecimal precoPorLitro) {
    return litragem
        .multiply(precoPorLitro)
        .setScale(2, RoundingMode.HALF_UP);
  }

  private BigDecimal calcularLitragem(
      BigDecimal valorTotal,
      BigDecimal precoPorLitro) {
    return valorTotal
        .divide(precoPorLitro, 3, RoundingMode.HALF_UP);
  }

  private BigDecimal[] calcularValoresAbastecimento(
      BigDecimal valorTotal,
      BigDecimal litragem,
      BigDecimal precoPorLitro) {
    if (valorTotal == null && litragem == null) {
      throw new RegraNegocioAbastecerException(
          "Informe o valor total ou a litragem do abastecimento!");
    }

    if (valorTotal != null && litragem != null) {
      BigDecimal valorCalculado = calcularValorTotal(
          litragem,
          precoPorLitro);

      if (valorCalculado.compareTo(
          valorTotal.setScale(2, RoundingMode.HALF_UP)) != 0) {
        throw new RegraNegocioAbastecerException(
            "O valor total não corresponde à litragem informada.");
      }
    }

    if (valorTotal == null) {
      valorTotal = calcularValorTotal(
          litragem,
          precoPorLitro);
    }

    if (litragem == null) {
      litragem = calcularLitragem(
          valorTotal,
          precoPorLitro);
    }

    return new BigDecimal[] {
        valorTotal,
        litragem
    };
  }
}