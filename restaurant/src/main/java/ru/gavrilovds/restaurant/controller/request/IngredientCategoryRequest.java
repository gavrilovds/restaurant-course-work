package ru.gavrilovds.restaurant.controller.request;

public record IngredientCategoryRequest(
    String name,
    Long restaurantId
) {

}
