package com.easycraft.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 4429339223326131921L;

  private String userId;
  private String token;
}
