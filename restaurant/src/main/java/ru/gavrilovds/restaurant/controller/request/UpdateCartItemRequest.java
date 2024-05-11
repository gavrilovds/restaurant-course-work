package ru.gavrilovds.restaurant.controller.request;

public record UpdateCartItemRequest(
    Long cartId,
    int quantity
) {

}
