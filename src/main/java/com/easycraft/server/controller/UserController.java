package com.easycraft.server.controller;

import com.easycraft.server.service.UserService;
import com.easycraft.server.user.User;
import com.easycraft.server.user.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class UserController {

  @Autowired
  public UserService userService;

  @PostMapping
  public ResponseEntity<User> createAccount(@RequestBody final UserCreateRequest request) {
    return ResponseEntity.ok(userService.register(request));
  }

}
