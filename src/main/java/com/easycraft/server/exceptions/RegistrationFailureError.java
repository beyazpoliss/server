package com.easycraft.server.exceptions;

public class RegistrationFailureError extends RuntimeException{

  public RegistrationFailureError(final String message, final Throwable cause) {
    super(message, cause);
  }

}
