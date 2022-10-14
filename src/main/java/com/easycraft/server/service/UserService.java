package com.easycraft.server.service;

import com.easycraft.server.repository.UserRepository;
import com.easycraft.server.security.PasswordEncoder;
import com.easycraft.server.user.User;
import com.easycraft.server.user.UserCreateRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final static String USER_NOT_FOUND_MSG =
    "user with email %s not found";

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .orElseThrow(() ->
        new UsernameNotFoundException(
          String.format(USER_NOT_FOUND_MSG, email)));
  }

  public User register(UserCreateRequest request) {
    boolean userExists = userRepository
      .findByEmail(request.getEmail())
      .isPresent();

    if (userExists) {
      throw new IllegalStateException("email already taken");
    }

    final var user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    final var encodedPassword = passwordEncoder.encoder().encode(request.getPassword());
    user.setPassword(encodedPassword);
    userRepository.save(user);
    return user;
  }
}
