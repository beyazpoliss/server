package com.easycraft.server.security.filter;

import com.easycraft.server.misc.Jackson;
import com.easycraft.server.misc.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class CustomAuthenticationFilter
 extends UsernamePasswordAuthenticationFilter {

  @Autowired
  public CustomAuthenticationFilter(
   final AuthenticationManager authenticationManager) {
    super(authenticationManager);
    this.setFilterProcessesUrl("/auth/login");
    this.setSessionAuthenticationStrategy(
     (authentication, request, response) -> {
       try {
         new Jackson.Jwt((UserDetails) authentication.getPrincipal(), request).write(response);
       } catch (final IOException e) {
         e.printStackTrace();
         throw new RuntimeException(
          "Something went wrong when writing the jwt to the response",
          e
         );
       }
     }
    );
    this.setAuthenticationFailureHandler((request, response, exception) -> {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(401);
      Jackson.MAPPER.writeValue(
       response.getOutputStream(),
       Map.of(
        "timestamp",
        Times.nowLocal().toString(),
        "status",
        401,
        "error",
        "Unauthorized",
        "message",
        "Please confirm your e mail first.",
        "path",
        "/auth/login"
       )
      );
    });
  }
}
