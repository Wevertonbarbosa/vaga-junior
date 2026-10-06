package br.com.merito.project_merito.controller;

import br.com.merito.project_merito.dto.request.AbastecimentoRequest;
import br.com.merito.project_merito.dto.response.AbastecimentoResponse;
import br.com.merito.project_merito.service.AbastecimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abastecimentos")
public class AbastecimentoController {

  private final AbastecimentoService abastecimentoService;

  public AbastecimentoController(
      AbastecimentoService abastecimentoService) {
    this.abastecimentoService = abastecimentoService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AbastecimentoResponse salvar(
      @RequestBody @Valid AbastecimentoRequest abastecimentoRequest) {
    return abastecimentoService.salvarAbastecimento(
        abastecimentoRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AbastecimentoResponse> listarTodos() {
    return abastecimentoService.listarTodos();
  }

  @GetMapping("/{id}")
  public AbastecimentoResponse buscarPorId(
      @PathVariable Long id) {
    return abastecimentoService.buscarPorId(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public AbastecimentoResponse atualizar(
      @PathVariable Long id,
      @RequestBody @Valid AbastecimentoRequest abastecimentoRequest) {
    return abastecimentoService.atualizar(
        id,
        abastecimentoRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(
      @PathVariable Long id) {
    abastecimentoService.excluir(id);
  }
}