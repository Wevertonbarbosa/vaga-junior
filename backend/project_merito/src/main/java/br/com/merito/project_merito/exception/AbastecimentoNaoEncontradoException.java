package br.com.merito.project_merito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AbastecimentoNaoEncontradoException extends RuntimeException {

  public AbastecimentoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

}
