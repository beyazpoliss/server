package com.easycraft.server.security;

import com.easycraft.server.repository.UserRepository;
import com.easycraft.server.security.filter.CustomAuthenticationFilter;
import com.easycraft.server.security.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public AuthenticationManager authenticationManager(
   final HttpSecurity http,
   final BCryptPasswordEncoder passwordEncoder,
   final UserDetailsService userDetailsService,
   final DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher
  ) throws Exception {
    return http
     .getSharedObject(AuthenticationManagerBuilder.class)
     .authenticationEventPublisher(defaultAuthenticationEventPublisher)
     .userDetailsService(userDetailsService)
     .passwordEncoder(passwordEncoder)
     .and()
     .build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(
   final ApplicationEventPublisher applicationEventPublisher
  ) {
    return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
   final HttpSecurity http,
   final AuthenticationManager authenticationManager
  ) throws Exception {
    return http
     .csrf()
     .disable()
     .sessionManagement()
     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     .and()
     .addFilter(new CustomAuthenticationFilter(authenticationManager))
     .addFilterAt(
      new CustomAuthorizationFilter(),
      UsernamePasswordAuthenticationFilter.class
     )
     .build();
  }

}
