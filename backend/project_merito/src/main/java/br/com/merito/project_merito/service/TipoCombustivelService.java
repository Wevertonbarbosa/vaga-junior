package br.com.merito.project_merito.service;

import br.com.merito.project_merito.domain.entity.TipoCombustivel;
import br.com.merito.project_merito.dto.request.TipoCombustivelRequest;
import br.com.merito.project_merito.dto.response.TipoCombustivelResponse;
import br.com.merito.project_merito.exception.CombustivelNaoEncontradoException;
import br.com.merito.project_merito.repository.TipoCombustivelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCombustivelService {

  private final TipoCombustivelRepository tipoCombustivelRepository;

  public TipoCombustivelService(
      TipoCombustivelRepository tipoCombustivelRepository) {
    this.tipoCombustivelRepository = tipoCombustivelRepository;
  }

  public TipoCombustivelResponse salvarTipoCombustivel(
      TipoCombustivelRequest tipoCombustivelRequest) {

    TipoCombustivel tipoCombustivel = new TipoCombustivel();

    // Copia os dados recebidos na requisição para a entidade
    // antes de realizar a persistência no banco de dados.
    BeanUtils.copyProperties(tipoCombustivelRequest, tipoCombustivel);

    TipoCombustivel tipoCombustivelSalvo = tipoCombustivelRepository.save(tipoCombustivel);

    return new TipoCombustivelResponse(
        tipoCombustivelSalvo.getId(),
        tipoCombustivelSalvo.getNome(),
        tipoCombustivelSalvo.getPrecoPorLitro());
  }

  public TipoCombustivelResponse buscarPorId(Long id) {

    // O Optional permite verificar se o combustível existe
    // antes de realizar o acesso aos seus dados.
    Optional<TipoCombustivel> tipoCombustivelOptional = tipoCombustivelRepository.findById(id);

    if (tipoCombustivelOptional.isPresent()) {

      TipoCombustivel tipoCombustivel = tipoCombustivelOptional.get();

      return new TipoCombustivelResponse(
          tipoCombustivel.getId(),
          tipoCombustivel.getNome(),
          tipoCombustivel.getPrecoPorLitro());

    } else {
      throw new CombustivelNaoEncontradoException(
          "Tipo de combustível não encontrado no banco de dados!");
    }
  }

  public List<TipoCombustivelResponse> listarTodos() {
    // Converte as entidades retornadas pelo banco em DTOs de resposta,
    // evitando expor diretamente as entidades da aplicação.
    return tipoCombustivelRepository
        .findAll()
        .stream()
        .map(tipoCombustivel -> new TipoCombustivelResponse(
            tipoCombustivel.getId(),
            tipoCombustivel.getNome(),
            tipoCombustivel.getPrecoPorLitro()))
        .toList();
  }

  public void excluir(Long id) {

    Optional<TipoCombustivel> tipoCombustivelOptional = tipoCombustivelRepository.findById(id);

    if (tipoCombustivelOptional.isPresent()) {

      tipoCombustivelRepository.delete(
          tipoCombustivelOptional.get());

    } else {
      throw new CombustivelNaoEncontradoException(
          "Tipo de combustível não encontrado para excluir!");
    }
  }

  public TipoCombustivelResponse atualizar(
      Long id,
      TipoCombustivelRequest tipoCombustivelRequest) {

    Optional<TipoCombustivel> tipoCombustivelOptional = tipoCombustivelRepository.findById(id);

    if (tipoCombustivelOptional.isPresent()) {

      TipoCombustivel tipoCombustivel = tipoCombustivelOptional.get();
      // Atualiza os dados da entidade existente com os valores recebidos
      // na requisição, mantendo o mesmo registro no banco.
      BeanUtils.copyProperties(
          tipoCombustivelRequest,
          tipoCombustivel);

      TipoCombustivel tipoCombustivelAtualizado = tipoCombustivelRepository.save(tipoCombustivel);

      return new TipoCombustivelResponse(
          tipoCombustivelAtualizado.getId(),
          tipoCombustivelAtualizado.getNome(),
          tipoCombustivelAtualizado.getPrecoPorLitro());

    } else {
      throw new CombustivelNaoEncontradoException(
          "Tipo de combustível não encontrado para atualizar!");
    }
  }
}