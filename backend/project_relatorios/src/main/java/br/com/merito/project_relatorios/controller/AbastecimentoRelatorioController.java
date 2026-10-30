
package br.com.merito.project_relatorios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.merito.project_relatorios.dto.response.AbastecimentoRecebidoResponse;
import br.com.merito.project_relatorios.service.AbastecimentoRelatorioService;

@RestController
@RequestMapping("/api/v002/abastecimentos")
public class AbastecimentoRelatorioController {

  private final AbastecimentoRelatorioService service;

  public AbastecimentoRelatorioController(
      AbastecimentoRelatorioService service) {
    this.service = service;
  }

  // Lista todos os abastecimentos recebidos.
  @GetMapping
  public ResponseEntity<List<AbastecimentoRecebidoResponse>> listar() {
    return ResponseEntity.ok(service.listar());
  }

  // Busca um abastecimento pelo ID gerado no Backend B.
  @GetMapping("/{id}")
  public ResponseEntity<AbastecimentoRecebidoResponse> buscarPorId(
      @PathVariable Long id) {

    return service.buscarPorId(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
