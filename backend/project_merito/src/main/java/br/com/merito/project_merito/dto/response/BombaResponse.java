package br.com.merito.project_merito.dto.response;

public record BombaResponse(

    Long id,
    String nome,
    Long tipoCombustivelId,
    String tipoCombustivelNome

) {
}