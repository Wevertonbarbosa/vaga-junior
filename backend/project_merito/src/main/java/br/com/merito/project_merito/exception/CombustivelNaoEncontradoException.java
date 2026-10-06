package br.com.merito.project_merito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CombustivelNaoEncontradoException extends RuntimeException {

  public CombustivelNaoEncontradoException(String mensagem) {
    super(mensagem);
  }
}