package ru.gavrilovds.restaurant.controller.request;

public record IngredientItemRequest(
    String name,
    Long categoryId,
    Long restaurantId
) {

}
