package com.easycraft.server.request;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record RegistrationRequest(@NotNull String username,
                                  @NotNull String crafterName,
                                  @NotNull String password,
                                  @NotNull String matchingPassword,
                                  @NotNull String email) implements Serializable {

}
