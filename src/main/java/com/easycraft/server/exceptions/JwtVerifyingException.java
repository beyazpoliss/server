package com.easycraft.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JwtVerifyingException extends RuntimeException {

  public JwtVerifyingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}