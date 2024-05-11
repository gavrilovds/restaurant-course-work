package ru.gavrilovds.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.SignInRequest;
import ru.gavrilovds.restaurant.controller.request.SignUpRequest;
import ru.gavrilovds.restaurant.controller.response.AuthResponse;
import ru.gavrilovds.restaurant.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public AuthResponse createUser(@RequestBody SignUpRequest request) {
    return authService.createUser(request);
  }

  @PostMapping("/signin")
  public AuthResponse signIn(@RequestBody SignInRequest request) {
    return authService.signIn(request);
  }
}
