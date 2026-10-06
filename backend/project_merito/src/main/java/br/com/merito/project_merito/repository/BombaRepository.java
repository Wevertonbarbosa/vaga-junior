package br.com.merito.project_merito.repository;

import br.com.merito.project_merito.domain.entity.Bomba;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BombaRepository extends JpaRepository<Bomba, Long> {
}