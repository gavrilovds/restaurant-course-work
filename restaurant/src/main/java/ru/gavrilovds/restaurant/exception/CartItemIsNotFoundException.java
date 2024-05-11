package ru.gavrilovds.restaurant.exception;

public class CartItemIsNotFoundException extends BaseException {

  public CartItemIsNotFoundException(Long cartItemId) {
    super("Cart item with id %d is not found".formatted(cartItemId));
  }
}
