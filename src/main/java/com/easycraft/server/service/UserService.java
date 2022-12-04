package com.easycraft.server.service;

import com.easycraft.server.exceptions.RegistrationFailureError;
import com.easycraft.server.misc.Validator;
import com.easycraft.server.model.User;
import com.easycraft.server.model.UserInfo;
import com.easycraft.server.repository.UserRepository;
import com.easycraft.server.request.RegistrationRequest;
import com.easycraft.server.response.RegistrationResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public record UserService(
 @NotNull UserRepository userRepository,
 @NotNull BCryptPasswordEncoder passwordEncoder
) implements UserDetailsService {

  @NotNull
  public RegistrationResponse registrationNewUser(@NotNull final RegistrationRequest registrationRequest){
    final var name = registrationRequest.username();
    final var crafterName = registrationRequest.crafterName();
    final var password = registrationRequest.password();
    final var matchPassword = registrationRequest.matchingPassword();
    final var email = registrationRequest.email();

    Validator.validate(name,crafterName,password,matchPassword,email);

    if (userRepository.findUserByUserName(name) != null){
      throw new RegistrationFailureError("This username is already in use.",new RuntimeException());
    }

    if (userRepository.findUserByCrafterName(crafterName) != null){
      throw new RegistrationFailureError("This CrafterName is already in use.",new RuntimeException());
    }

    if (userRepository.findUserByEmail(email) != null){
      throw new RegistrationFailureError("This E-Mail is already in use.",new RuntimeException());
    }

    final User user = User.builder()
     .id(UUID.randomUUID().toString())
     .username(name)
     .crafterName(crafterName)
     .email(email)
     .accountCreateDate(new Date())
     .userDateOfBirth(new Date())
     .password(passwordEncoder.encode(password))
     .role("USER").build();

    userRepository.save(user);

    return new RegistrationResponse(true,"Account is created!",201,user);
  }

  @NotNull
  public User load(@NotNull final String userName, @NotNull final String password){
    return userRepository
     .findUserByUsernameAndPassword(userName, password);
  }

  @NotNull
  public List<UserInfo> accounts(){
    return userRepository
     .findAll()
     .parallelStream()
     .map(user -> new UserInfo(user.getCrafterName(),user.getRole()))
     .collect(Collectors.toList()
     );
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional
     .ofNullable(userRepository.findUserByUserName(username))
     .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND!")
     );
  }
}
