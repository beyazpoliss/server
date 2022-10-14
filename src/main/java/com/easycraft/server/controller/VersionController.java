package com.easycraft.server.controller;

import com.easycraft.server.service.UserService;
import com.easycraft.server.service.VersionService;
import com.easycraft.server.user.User;
import com.easycraft.server.user.UserCreateRequest;
import com.easycraft.server.version.VersionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

  @Autowired
  public VersionService versionService;

  @PostMapping
  public ResponseEntity<String> hasUpdate(@RequestBody final VersionRequest request) {
    return ResponseEntity.ok(versionService.hasUpdate(request));
  }


}
