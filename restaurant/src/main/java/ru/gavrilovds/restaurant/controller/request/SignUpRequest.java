package ru.gavrilovds.restaurant.controller.request;

import ru.gavrilovds.restaurant.entity.Role;

public record SignUpRequest(

    String fullName,

    String email,

    String password,

    Role role

) {

}
