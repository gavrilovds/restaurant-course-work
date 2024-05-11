package ru.gavrilovds.restaurant.controller.request;

import java.util.List;

public record AddCartItemRequest(
    Long foodId,
    int quantity,
    List<String> ingredients
) {

}
