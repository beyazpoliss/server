package com.easycraft.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
  USER("USER"),ADMIN("ADMIN");

  @Getter
  private final String name;

}
