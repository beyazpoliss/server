package com.easycraft.server.response;

import com.easycraft.server.exceptions.RegistrationFailureError;
import com.easycraft.server.model.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NotNull
@Getter
@Setter
public class RegistrationResponse {

  private boolean create;
  private String message;
  private int status;

  private User user;

  public RegistrationResponse(RegistrationFailureError ex) {
    this.create = false;
    this.message = ex.getMessage();
    this.status = 406;
  }

  public RegistrationResponse(boolean created, String message, int status,User user){
    this.status = status;
    this.create = created;
    this.message = message;
    this.user = user.setPassword("vanished");
  }
}
