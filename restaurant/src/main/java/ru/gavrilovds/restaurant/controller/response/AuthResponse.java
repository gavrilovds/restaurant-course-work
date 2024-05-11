package ru.gavrilovds.restaurant.controller.response;

public record AuthResponse(
    String jwt,
    String message,
    String role
) {

}
