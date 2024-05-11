package ru.gavrilovds.restaurant.exception;

public class IngredientsItemNotFoundException extends BaseException {

  public IngredientsItemNotFoundException(Long id) {
    super("Ingredients item with id %d is not found".formatted(id));
  }
}
