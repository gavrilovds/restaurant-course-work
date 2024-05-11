package ru.gavrilovds.restaurant.controller.request;

import java.util.List;
import ru.gavrilovds.restaurant.entity.CategoryEntity;
import ru.gavrilovds.restaurant.entity.IngredientsItemEntity;

public record CreateFoodRequest(
    String name,
    String description,
    Long price,
    CategoryEntity category,
    List<String> images,
    Long restaurantId,
    boolean isVegetarian,

    boolean isSeasonal,
    List<IngredientsItemEntity> ingredients
) {

}
