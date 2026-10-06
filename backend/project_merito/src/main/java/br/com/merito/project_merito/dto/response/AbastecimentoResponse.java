package br.com.merito.project_merito.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AbastecimentoResponse(

    Long id,
    Long bombaId,
    String bombaNome,

    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataAbastecimento,

    BigDecimal valorTotal,
    BigDecimal litragem

) {
}