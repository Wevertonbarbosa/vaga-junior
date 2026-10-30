
package br.com.merito.project_relatorios.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "abastecimentos_recebidos")
@Getter
@Setter
@NoArgsConstructor
public class AbastecimentoRecebido {
  // identificador gerado pelo próprio banco do Backend B.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // ID original recebido do Backend A.
  // Unique evita registrar o mesmo abastecimento duas vezes.
  @Column(nullable = false, unique = true)
  private Long abastecimentoId;

  @Column(nullable = false)
  private Long bombaId;

  @Column(nullable = false, precision = 10, scale = 3)
  private BigDecimal volume;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal valorTotal;

  @Column(nullable = false)
  private LocalDateTime dataAbastecimento;
}
