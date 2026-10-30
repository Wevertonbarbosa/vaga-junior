
package br.com.merito.project_relatorios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.merito.project_relatorios.domain.entity.AbastecimentoRecebido;
import br.com.merito.project_relatorios.domain.repository.AbastecimentoRecebidoRepository;
import br.com.merito.project_relatorios.dto.event.AbastecimentoCriadoEvent;
import br.com.merito.project_relatorios.dto.response.AbastecimentoRecebidoResponse;

@Service
public class AbastecimentoRelatorioService {

  private final AbastecimentoRecebidoRepository repository;

  public AbastecimentoRelatorioService(
      AbastecimentoRecebidoRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void salvar(AbastecimentoCriadoEvent evento) {

    // Evita salvar novamente um evento já processado.
    if (repository.existsByAbastecimentoId(evento.abastecimentoId())) {
      return;
    }

    AbastecimentoRecebido abastecimento = new AbastecimentoRecebido();

    abastecimento.setAbastecimentoId(evento.abastecimentoId());
    abastecimento.setBombaId(evento.bombaId());
    abastecimento.setVolume(evento.volume());
    abastecimento.setValorTotal(evento.valorTotal());
    abastecimento.setDataAbastecimento(evento.dataAbastecimento());

    repository.save(abastecimento);
  }

  public List<AbastecimentoRecebidoResponse> listar() {

    return repository.findAll()
        .stream()
        .map(this::converterParaResponse)
        .toList();
  }

  public Optional<AbastecimentoRecebidoResponse> buscarPorId(Long id) {

    return repository.findById(id)
        .map(this::converterParaResponse);
  }

  // Converte a entidade em DTO de resposta.
  private AbastecimentoRecebidoResponse converterParaResponse(
      AbastecimentoRecebido abastecimento) {

    return new AbastecimentoRecebidoResponse(
        abastecimento.getId(),
        abastecimento.getAbastecimentoId(),
        abastecimento.getBombaId(),
        abastecimento.getVolume(),
        abastecimento.getValorTotal(),
        abastecimento.getDataAbastecimento());
  }

}
