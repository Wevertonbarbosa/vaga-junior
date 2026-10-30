package br.com.merito.project_relatorios.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Representa o evento recebido do Backend A através do Kafka.
// Seus campos devem corresponder ao JSON publicado pelo Producer.
public record AbastecimentoCriadoEvent(

    Long abastecimentoId,

    Long bombaId,

    BigDecimal volume,

    BigDecimal valorTotal,

    LocalDateTime dataAbastecimento

) {
}