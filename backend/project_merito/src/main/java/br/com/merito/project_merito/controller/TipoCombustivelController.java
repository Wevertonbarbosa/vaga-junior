package br.com.merito.project_merito.controller;

import br.com.merito.project_merito.dto.request.TipoCombustivelRequest;
import br.com.merito.project_merito.dto.response.TipoCombustivelResponse;
import br.com.merito.project_merito.service.TipoCombustivelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-combustiveis")
public class TipoCombustivelController {

  private final TipoCombustivelService tipoCombustivelService;

  public TipoCombustivelController(TipoCombustivelService tipoCombustivelService) {
    this.tipoCombustivelService = tipoCombustivelService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TipoCombustivelResponse salvar(
      @RequestBody @Valid TipoCombustivelRequest tipoCombustivelRequest) {
    return tipoCombustivelService.salvarTipoCombustivel(
        tipoCombustivelRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TipoCombustivelResponse> listarTodos() {
    return tipoCombustivelService.listarTodos();
  }

  @GetMapping("/{id}")
  public TipoCombustivelResponse buscarPorId(
      @PathVariable Long id) {
    return tipoCombustivelService.buscarPorId(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    tipoCombustivelService.excluir(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TipoCombustivelResponse atualizar(
      @PathVariable Long id,
      @RequestBody @Valid TipoCombustivelRequest tipoCombustivelRequest) {
    return tipoCombustivelService.atualizar(
        id,
        tipoCombustivelRequest);
  }
}