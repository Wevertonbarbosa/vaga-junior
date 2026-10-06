package br.com.merito.project_merito.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoCombustivelRequest(
    @NotBlank(message = "O nome do combustível é obrigatório") String nome,
    @NotNull(message = "O preço por litro é obrigatório")
     
    @DecimalMin(value = "0.01", message = "O preço por litro deve ser maior que zero") BigDecimal precoPorLitro) {

}
