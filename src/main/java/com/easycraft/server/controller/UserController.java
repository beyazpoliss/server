package com.easycraft.server.controller;

import com.easycraft.server.model.UserInfo;
import com.easycraft.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService accountService;

  @GetMapping("/version")
  public String version() {
    return "1,0";
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserInfo>> publicUsers(){
    return ResponseEntity.ok(accountService.accounts());
  }

}
