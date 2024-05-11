package ru.gavrilovds.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.response.GetProfileResponse;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private static final String HEADER = "Authorization";
  private final UserService userService;


  @GetMapping("/profile")
  public UserEntity getProfile(@RequestHeader(HEADER) String jwt) {
    return userService.getProfile(jwt);
  }
}
