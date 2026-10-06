package br.com.merito.project_merito.repository;

import br.com.merito.project_merito.domain.entity.Abastecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {
}