package br.com.merito.project_merito.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BombaRequest(

    @NotBlank(message = "O nome da bomba é obrigatório") String nome,

    @NotNull(message = "O tipo de combustível é obrigatório") Long tipoCombustivelId

) {
}