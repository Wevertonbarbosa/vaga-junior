package br.com.merito.project_merito.controller;

import br.com.merito.project_merito.dto.request.BombaRequest;
import br.com.merito.project_merito.dto.response.BombaResponse;
import br.com.merito.project_merito.service.BombaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bombas")
public class BombaController {

  private final BombaService bombaService;

  public BombaController(BombaService bombaService) {
    this.bombaService = bombaService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BombaResponse salvar(
      @RequestBody @Valid BombaRequest bombaRequest) {
    return bombaService.salvarBomba(bombaRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<BombaResponse> listarTodos() {
    return bombaService.listarTodos();
  }

  @GetMapping("/{id}")
  public BombaResponse buscarPorId(
      @PathVariable Long id) {
    return bombaService.buscarPorId(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public BombaResponse atualizar(
      @PathVariable Long id,
      @RequestBody @Valid BombaRequest bombaRequest) {
    return bombaService.atualizar(
        id,
        bombaRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(
      @PathVariable Long id) {
    bombaService.excluir(id);
  }
}