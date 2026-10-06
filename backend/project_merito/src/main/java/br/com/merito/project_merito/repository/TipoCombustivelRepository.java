package br.com.merito.project_merito.repository;

import br.com.merito.project_merito.domain.entity.TipoCombustivel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombustivelRepository extends JpaRepository<TipoCombustivel, Long> {
}