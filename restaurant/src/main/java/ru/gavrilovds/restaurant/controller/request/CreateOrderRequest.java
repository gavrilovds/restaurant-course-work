package ru.gavrilovds.restaurant.controller.request;

import ru.gavrilovds.restaurant.entity.AddressEntity;

public record CreateOrderRequest(
    Long restaurantId,
    AddressEntity deliveryAddress
) {

}
