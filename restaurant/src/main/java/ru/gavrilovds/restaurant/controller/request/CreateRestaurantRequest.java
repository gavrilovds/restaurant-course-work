package ru.gavrilovds.restaurant.controller.request;

import java.util.List;
import ru.gavrilovds.restaurant.entity.AddressEntity;
import ru.gavrilovds.restaurant.entity.ContactInformation;

public record CreateRestaurantRequest(
    String name,
    String description,
    String cuisineType,
    AddressEntity address,
    ContactInformation contactInformation,
    String openingHours,
    List<String> images
) {

}
