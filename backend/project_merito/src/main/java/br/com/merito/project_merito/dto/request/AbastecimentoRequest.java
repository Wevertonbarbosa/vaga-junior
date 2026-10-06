package br.com.merito.project_merito.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AbastecimentoRequest(

        @NotNull(message = "A bomba é obrigatória") Long bombaId,

        @NotNull(message = "A data do abastecimento é obrigatória") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataAbastecimento,

        @DecimalMin(value = "0.01", message = "O valor total deve ser maior que zero") BigDecimal valorTotal,

        @DecimalMin(value = "0.01", message = "A litragem deve ser maior que zero") BigDecimal litragem

) {
}