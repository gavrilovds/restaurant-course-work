package ru.gavrilovds.restaurant.controller.request;

public record SignInRequest(
    String email,
    String password
) {

}
