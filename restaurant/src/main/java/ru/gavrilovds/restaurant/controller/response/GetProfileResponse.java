package ru.gavrilovds.restaurant.controller.response;

import java.util.List;
import ru.gavrilovds.restaurant.dto.RestaurantDto;
import ru.gavrilovds.restaurant.entity.Role;

public record GetProfileResponse(
    Long id,

    String fullName,

    String email,
    String password,

    Role role,

    List<RestaurantDto> favorites
) {

}
