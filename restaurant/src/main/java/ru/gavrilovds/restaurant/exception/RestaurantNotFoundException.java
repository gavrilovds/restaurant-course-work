package ru.gavrilovds.restaurant.exception;

public class RestaurantNotFoundException extends BaseException {

  public RestaurantNotFoundException(Long userId) {
    super("Restaurant with owner id %d is not found".formatted(userId));
  }
}
