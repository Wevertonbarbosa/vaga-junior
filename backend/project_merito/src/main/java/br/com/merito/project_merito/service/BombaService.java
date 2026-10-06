package br.com.merito.project_merito.service;

import br.com.merito.project_merito.domain.entity.Bomba;
import br.com.merito.project_merito.domain.entity.TipoCombustivel;
import br.com.merito.project_merito.dto.request.BombaRequest;
import br.com.merito.project_merito.dto.response.BombaResponse;
import br.com.merito.project_merito.exception.BombaNaoEncontradaException;
import br.com.merito.project_merito.exception.CombustivelNaoEncontradoException;
import br.com.merito.project_merito.repository.BombaRepository;
import br.com.merito.project_merito.repository.TipoCombustivelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BombaService {

  private final BombaRepository bombaRepository;
  private final TipoCombustivelRepository tipoCombustivelRepository;

  public BombaService(
      BombaRepository bombaRepository,
      TipoCombustivelRepository tipoCombustivelRepository) {
    this.bombaRepository = bombaRepository;
    this.tipoCombustivelRepository = tipoCombustivelRepository;
  }

  public BombaResponse salvarBomba(BombaRequest bombaRequest) {
    // A bomba deve estar associada a um tipo de combustível existente.
    // A validação do relacionamento é feita antes de salvar a bomba.
    TipoCombustivel tipoCombustivel = buscarTipoCombustivel(bombaRequest.tipoCombustivelId());

    Bomba bomba = new Bomba();

    // Copia os dados básicos da requisição para a entidade.
    // O tipo de combustível é definido separadamente por ser um relacionamento.
    BeanUtils.copyProperties(bombaRequest, bomba);

    bomba.setTipoCombustivel(tipoCombustivel);

    Bomba bombaSalva = bombaRepository.save(bomba);

    return converterParaResponse(bombaSalva);
  }

  public BombaResponse buscarPorId(Long id) {

    Optional<Bomba> bombaOptional = bombaRepository.findById(id);

    if (bombaOptional.isPresent()) {

      return converterParaResponse(bombaOptional.get());

    } else {
      throw new BombaNaoEncontradaException(
          "Bomba não encontrada no banco de dados!");
    }
  }

  public List<BombaResponse> listarTodos() {

    return bombaRepository
        .findAll()
        .stream()
        .map(this::converterParaResponse)
        .toList();
  }

  public void excluir(Long id) {

    Optional<Bomba> bombaOptional = bombaRepository.findById(id);

    if (bombaOptional.isPresent()) {

      bombaRepository.delete(bombaOptional.get());

    } else {
      throw new BombaNaoEncontradaException(
          "Bomba não encontrada para excluir!");
    }
  }

  public BombaResponse atualizar(
      Long id,
      BombaRequest bombaRequest) {

    Optional<Bomba> bombaOptional = bombaRepository.findById(id);

    if (bombaOptional.isPresent()) {

      Bomba bomba = bombaOptional.get();

      TipoCombustivel tipoCombustivel = buscarTipoCombustivel(
          bombaRequest.tipoCombustivelId());

      BeanUtils.copyProperties(bombaRequest, bomba);

      bomba.setTipoCombustivel(tipoCombustivel);

      Bomba bombaAtualizada = bombaRepository.save(bomba);

      return converterParaResponse(bombaAtualizada);

    } else {
      throw new BombaNaoEncontradaException(
          "Bomba não encontrada para atualizar!");
    }
  }

  // Busca o tipo de combustível utilizado pela bomba.
  // Caso não exista, a operação é interrompida para evitar
  // um relacionamento inválido no banco de dados.
  private TipoCombustivel buscarTipoCombustivel(
      Long tipoCombustivelId) {

    Optional<TipoCombustivel> tipoCombustivelOptional = tipoCombustivelRepository.findById(tipoCombustivelId);

    if (tipoCombustivelOptional.isPresent()) {

      return tipoCombustivelOptional.get();

    } else {
      throw new CombustivelNaoEncontradoException(
          "Tipo de combustível não encontrado no banco de dados!");
    }
  }

  private BombaResponse converterParaResponse(Bomba bomba) {

    return new BombaResponse(
        bomba.getId(),
        bomba.getNome(),
        bomba.getTipoCombustivel().getId(),
        bomba.getTipoCombustivel().getNome());
  }
}