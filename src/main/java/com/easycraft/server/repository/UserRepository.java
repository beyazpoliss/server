package com.easycraft.server.repository;

import com.easycraft.server.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

  @Override
  @NotNull
  Optional<User> findById(@NotNull final Long id);

  @Query("{username : ?0, password : ?1}")
  User findUserByUsernameAndPassword(@NotNull final String username, @NotNull final String password);

  User findByUsername(@NotNull final String username);

  @Query("{crafterName : ?0}")
  User findUserByCrafterName(@NotNull final String crafterName);

  @Query("{email : ?0}")
  User findUserByEmail(@NotNull final String email);

  @Override
  @NotNull
  List<User> findAll();
}
