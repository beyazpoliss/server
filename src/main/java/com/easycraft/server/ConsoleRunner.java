package com.easycraft.server;

import com.easycraft.server.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner {

  @Bean
  public CommandLineRunner run(@NotNull final BCryptPasswordEncoder encoder, @NotNull final UserService userService){
    return args -> {
      System.out.println("Application Started.");
    };
  }

}
