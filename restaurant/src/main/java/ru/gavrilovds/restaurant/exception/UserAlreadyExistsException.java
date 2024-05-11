package ru.gavrilovds.restaurant.exception;

public class UserAlreadyExistsException extends BaseException {

  public UserAlreadyExistsException(String email) {
    super("User with email %s already exists".formatted(email));
  }
}
