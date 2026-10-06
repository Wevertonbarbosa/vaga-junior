package br.com.merito.project_merito.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bombas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bomba {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nome;

  @ManyToOne
  @JoinColumn(name = "tipo_combustivel_id", nullable = false)
  private TipoCombustivel tipoCombustivel;
}