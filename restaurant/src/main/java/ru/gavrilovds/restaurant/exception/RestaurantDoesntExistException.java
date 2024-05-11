package ru.gavrilovds.restaurant.exception;

public class RestaurantDoesntExistException extends BaseException {

  public RestaurantDoesntExistException(Long restaurantId) {
    super("Restaurant with id %d doesnt exists".formatted(restaurantId));
  }
}
