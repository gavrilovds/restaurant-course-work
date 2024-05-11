package ru.gavrilovds.restaurant.exception;

public class CategoryNotFoundException extends BaseException {

  public CategoryNotFoundException(Long categoryId) {
    super("Category with id %d is not found".formatted(categoryId));
  }
}
