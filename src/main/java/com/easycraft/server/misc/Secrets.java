package com.easycraft.server.misc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface Secrets {
  String BEARER = "Bearer ";

  int BEARER_LENGTH = Secrets.BEARER.length();

  String SECRET = "{0-0-0o_#_o0-0-0}";

  Algorithm ALGORITHM = Algorithm.HMAC256(Secrets.SECRET.getBytes());

  static String generateAccessToken(
   @NotNull final HttpServletRequest request,
   @NotNull final UserDetails user
  ) {
    return JWT
     .create()
     .withSubject(user.getUsername())
     .withExpiresAt(new Date(Times.nowMs() + Times.DAY_BY_MS))
     .withIssuer(request.getRequestURL().toString())
     .withClaim(
      "roles",
      user
       .getAuthorities()
       .stream()
       .map(GrantedAuthority::getAuthority)
       .toList()
     )
     .sign(Secrets.ALGORITHM);
  }

  static String generateRefreshToken(
   @NotNull final HttpServletRequest request,
   @NotNull final UserDetails user
  ) {
    return JWT
     .create()
     .withSubject(user.getUsername())
     .withIssuer(request.getRequestURL().toString())
     .sign(Secrets.ALGORITHM);
  }
}