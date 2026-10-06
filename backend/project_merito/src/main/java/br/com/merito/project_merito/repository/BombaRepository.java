package br.com.merito.project_merito.repository;

import br.com.merito.project_merito.domain.entity.Bomba;
import org.springframework.data.jpa.repository.JpaRepository;

// O JpaRepository já fornece a camada de acesso aos dados (DAO),
// portanto não é necessário criar uma classe DAO separada.
public interface BombaRepository extends JpaRepository<Bomba, Long> {
}