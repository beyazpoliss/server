package com.easycraft.server.misc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Jackson {
  ObjectMapper MAPPER = new JsonMapper();

  @JsonSerialize
  record Jwt(String accessToken, String refreshToken) {
    public Jwt(final UserDetails user, final HttpServletRequest request) {
      this(
       Secrets.generateAccessToken(request, user),
       Secrets.generateRefreshToken(request, user)
      );
    }
    public void write(final HttpServletResponse response) throws IOException {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      Jackson.MAPPER.writeValue(response.getOutputStream(), this);
    }
  }
}