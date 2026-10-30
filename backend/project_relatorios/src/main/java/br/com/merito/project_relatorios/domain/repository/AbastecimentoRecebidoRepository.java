
package br.com.merito.project_relatorios.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.merito.project_relatorios.domain.entity.AbastecimentoRecebido;

public interface AbastecimentoRecebidoRepository extends JpaRepository<AbastecimentoRecebido, Long> {

  boolean existsByAbastecimentoId(Long abastecimentoId);
}
