package com.easycraft.server.controller;

import com.easycraft.server.exceptions.RegistrationFailureError;
import com.easycraft.server.request.RegistrationRequest;
import com.easycraft.server.response.RegistrationResponse;
import com.easycraft.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
public class RegistrationController {

  @Autowired
  private UserService userService;

  @RequestMapping(path = "/registration", method = RequestMethod.POST)
  public ResponseEntity<RegistrationResponse> registration(HttpServletResponse httpServletResponse, @RequestBody RegistrationRequest registrationRequest) {
    try {
      final var response = userService.registrationNewUser(registrationRequest);
      return ResponseEntity.ok().body(response);
    } catch (RegistrationFailureError ex) {
      return ResponseEntity.badRequest().body(new RegistrationResponse(ex));
    }
  }
}
