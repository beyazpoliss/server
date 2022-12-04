package com.easycraft.server.security.filter;

import java.io.IOException;
import java.util.Arrays;

import com.easycraft.server.misc.Exceptions;
import com.easycraft.server.misc.Secrets;
import io.micrometer.common.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthorizationFilter
 extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) {
    final var servletPath = request.getServletPath();
    if (servletPath.equals("/auth/login") || servletPath.equals("/api/users") || servletPath.equals("/api/registration")) {
      return true;
    }
    final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    return StringUtils.isBlank(header) || !header.startsWith(Secrets.BEARER);
  }

  @Override
  protected void doFilterInternal(
   @NotNull final HttpServletRequest request,
   @NonNull final HttpServletResponse response,
   final FilterChain filterChain
  ) throws ServletException, IOException {
    final var token = request
     .getHeader(HttpHeaders.AUTHORIZATION)
     .substring(Secrets.BEARER_LENGTH);
    final var jwt = Exceptions.jwtVerifying(token);
    final var roles = Arrays
     .stream(jwt.getClaim("roles").asArray(String.class))
     .map(SimpleGrantedAuthority::new)
     .toList();
    final var authentication = new UsernamePasswordAuthenticationToken(
     jwt.getSubject(),
     jwt,
     roles
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}