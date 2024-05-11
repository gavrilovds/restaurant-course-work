package ru.gavrilovds.restaurant.exception;

public class CartNotFoundException extends BaseException {

  public CartNotFoundException(Long id) {
    super("Cart with id %d is not found".formatted(id));
  }
}
