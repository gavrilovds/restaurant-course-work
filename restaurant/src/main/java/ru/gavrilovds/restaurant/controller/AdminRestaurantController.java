package ru.gavrilovds.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.CreateRestaurantRequest;
import ru.gavrilovds.restaurant.controller.response.MessageResponse;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.RestaurantService;
import ru.gavrilovds.restaurant.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

  private static final String HEADER = "Authorization";

  private final RestaurantService restaurantService;
  private final UserService userService;

  @PostMapping
  public RestaurantEntity createRestaurant(@RequestBody CreateRestaurantRequest request,
      @RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return restaurantService.createRestaurant(request, user);
  }

  @PutMapping("/{id}")
  public RestaurantEntity updateRestaurant(@RequestBody CreateRestaurantRequest request,
      @RequestHeader(HEADER) String jwt, @PathVariable Long id) {
    return restaurantService.updateRestaurant(id, request);
  }

  @DeleteMapping("/{id}")
  public MessageResponse deleteRestaurant(@PathVariable Long id) {
    restaurantService.deleteRestaurant(id);
    return new MessageResponse("Restaurant has deleted successfully");
  }

  @PutMapping("/{id}/status")
  public RestaurantEntity updateRestaurantStatus(@RequestHeader(HEADER) String jwt,
      @PathVariable Long id) {
    return restaurantService.updateRestaurantStatus(id);
  }

  @GetMapping("/user")
  public RestaurantEntity findRestaurantByUserId(@RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return restaurantService.findRestaurantByUserId(user.getId());
  }


}
