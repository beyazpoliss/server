package com.easycraft.server.misc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.easycraft.server.exceptions.JwtVerifyingException;

public interface Exceptions {

  static DecodedJWT jwtVerifying(final String token) {
    try {
      return JWT.require(Secrets.ALGORITHM).build().verify(token);
    } catch (final Exception e) {
      throw new JwtVerifyingException(Messages.JWT_VERIFYING_ERROR.formatted(e.getMessage()), e);
    }
  }


}