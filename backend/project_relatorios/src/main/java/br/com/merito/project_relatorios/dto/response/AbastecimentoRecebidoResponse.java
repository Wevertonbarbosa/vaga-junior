
package br.com.merito.project_relatorios.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Define os dados que serão devolvidos pela API.
public record AbastecimentoRecebidoResponse(
    Long id,
    Long abastecimentoId,
    Long bombaId,
    BigDecimal volume,
    BigDecimal valorTotal,
    LocalDateTime dataAbastecimento) {
}
