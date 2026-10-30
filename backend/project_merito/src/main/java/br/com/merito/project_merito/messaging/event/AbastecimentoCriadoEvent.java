
package br.com.merito.project_merito.messaging.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Representa um evento publicado quando um abastecimento é criado.
// Record é uma classe imutável, adequada para transportar dados.
public record AbastecimentoCriadoEvent(

    Long abastecimentoId,

    Long bombaId,

    BigDecimal volume,

    BigDecimal valorTotal,

    LocalDateTime dataAbastecimento

) {
}
