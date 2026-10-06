package br.com.merito.project_merito.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "abastecimentos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Abastecimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "bomba_id", nullable = false)
  private Bomba bomba;

  @Column(nullable = false)
  private LocalDate dataAbastecimento;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal valorTotal;

  @Column(nullable = false, precision = 10, scale = 3)
  private BigDecimal litragem;
}