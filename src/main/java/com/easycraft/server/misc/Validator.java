package com.easycraft.server.misc;

import com.easycraft.server.exceptions.RegistrationFailureError;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public interface Validator {

  static void cannotSymbol(@NotNull final String text) {
    if (text.matches(".*[@#$%].*")){
      throw new RegistrationFailureError("One of the characters cannot contain a symbol: " + text, new RuntimeException());
    }
  }

  static void emailValidator(@NotNull final String email){
    final var regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    if (!patternMatches(email,regexPattern)){
      throw new RegistrationFailureError("Email is not valid: " + email, new RuntimeException());
    }
  }

  static void matchPassword(@NotNull final String password, @NotNull final String matchPassword){
    if (!password.equalsIgnoreCase(matchPassword)){
      throw new RegistrationFailureError("Passwords are not the same: " + matchPassword, new RuntimeException());
    }
  }

  static void isAscii(@NotNull final String text) {
    if (!StandardCharsets.US_ASCII.newEncoder().canEncode(text)){
      throw new RegistrationFailureError("Characters must be ASCII only: " + text, new RuntimeException());
    }
  }

  static void cannotSpaces(@NotNull final String text){
    if (text.contains(" ")) {
      throw new RegistrationFailureError("paswordword does not contain spaces: " + text, new RuntimeException());
    }
  }

  static void containLength(@NotNull final String text,int max,int min) {
    if (text.length() > max) {
      throw new RegistrationFailureError("Can be up to " + max +" long: " + text, new RuntimeException());
    }

    if (text.length() < min) {
      throw new RegistrationFailureError("Cannot be less than "+ min + " characters: " + text, new RuntimeException());
    }
  }

  static void mustCharacter(String text) {
    if (!text.matches(".*\\d.*")){
      throw new RegistrationFailureError("One of the characters must be a number: " + text, new RuntimeException());
    }
  }

  static void password(@NotNull final String password) {
    Validator.cannotSpaces(password);
    Validator.containLength(password,16,3);
    Validator.mustCharacter(password);
    Validator.cannotSymbol(password);
    Validator.isAscii(password);
  }

  static void username(@NotNull final String userName){
    Validator.cannotSymbol(userName);
    Validator.cannotSymbol(userName);
    Validator.containLength(userName,12,3);
    Validator.mustCharacter(userName);
    Validator.isAscii(userName);
  }

  static boolean patternMatches(@NotNull final String emailAddress,@NotNull final String regexPattern) {
    return Pattern.compile(regexPattern)
     .matcher(emailAddress)
     .matches();
  }

  static void email(@NotNull final String email){
    Validator.cannotSpaces(email);
    emailValidator(email);
    Validator.containLength(email,32,5);
  }

  static void crafterName(@NotNull final String crafterName){
    Validator.cannotSymbol(crafterName);
    Validator.cannotSpaces(crafterName);
    Validator.containLength(crafterName,16,3);
    Validator.isAscii(crafterName);
  }

  static void validate(@NotNull final String name,
                       @NotNull final String crafterName, @NotNull final String password,
                       @NotNull final String matchPassword, @NotNull final String email){
    Validator.username(name);
    Validator.crafterName(crafterName);
    Validator.password(password);
    Validator.matchPassword(password,matchPassword);
    Validator.email(email);
  }

}
