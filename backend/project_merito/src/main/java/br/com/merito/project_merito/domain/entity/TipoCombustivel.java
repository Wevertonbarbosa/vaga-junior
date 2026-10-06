package br.com.merito.project_merito.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tipos_combustiveis")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCombustivel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nome;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal precoPorLitro;

}
