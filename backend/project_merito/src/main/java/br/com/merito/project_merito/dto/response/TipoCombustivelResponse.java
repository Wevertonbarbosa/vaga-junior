package br.com.merito.project_merito.dto.response;

import java.math.BigDecimal;

public record TipoCombustivelResponse(
    Long id,
    String nome,
    BigDecimal precoPorLitro) {
}