package ru.gavrilovds.restaurant.exception;

public class FoodNotFoundException extends BaseException {

  public FoodNotFoundException(Long foodId) {
    super("Food with id %d is not found".formatted(foodId));
  }
}
