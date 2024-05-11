package ru.gavrilovds.restaurant.exception;

public class IngredientCategoryNotFoundException extends BaseException {

  public IngredientCategoryNotFoundException(Long id) {
    super("Ingredient Category with id %d is not found".formatted(id));
  }
}
