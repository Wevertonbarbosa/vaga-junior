package br.com.merito.project_merito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> manusearArgumentosInvalidos(
      MethodArgumentNotValidException erro) {

    Map<String, String> mapaDeErro = new HashMap<>();

    List<FieldError> campos = erro.getBindingResult().getFieldErrors();

    for (FieldError campo : campos) {
      mapaDeErro.put(
          campo.getField(),
          campo.getDefaultMessage());
    }

    return mapaDeErro;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(CombustivelNaoEncontradoException.class)
  public Map<String, String> manusearCombustivelNaoEncontrado(
      CombustivelNaoEncontradoException erro) {

    Map<String, String> mapaDeErro = new HashMap<>();

    mapaDeErro.put("erro", erro.getMessage());

    return mapaDeErro;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(BombaNaoEncontradaException.class)
  public Map<String, String> manusearBombasNaoEncontrado(
      BombaNaoEncontradaException erro) {

    Map<String, String> mapaDeErro = new HashMap<>();

    mapaDeErro.put("erro", erro.getMessage());

    return mapaDeErro;
  }

}