package ru.gavrilovds.restaurant.exception;

public class UserNotFoundException extends BaseException {

  public UserNotFoundException(String email) {
    super("User with email %s is not found".formatted(email));
  }
}
