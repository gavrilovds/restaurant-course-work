package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.dto.RestaurantDto;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.RestaurantService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

  private static final String HEADER = "Authorization";

  private final RestaurantService restaurantService;
  private final UserService userService;

  @GetMapping("/search")
  public List<RestaurantEntity> searchRestaurants(
      @RequestHeader(HEADER) String jwt,
      @RequestParam String query
  ) {
    return restaurantService.searchRestaurant(query);
  }

  @GetMapping
  public List<RestaurantEntity> getAllRestaurants(@RequestHeader(HEADER) String jwt) {
    return restaurantService.getAllRestaurants();
  }

  @GetMapping("/{id}")
  public RestaurantEntity findRestaurantById(@RequestHeader(HEADER) String jwt,
      @PathVariable Long id) {
    return restaurantService.findRestaurantById(id);
  }


  @GetMapping("/{id}/add-favourites")
  public RestaurantDto addToFavourites(@RequestHeader(HEADER) String jwt,
      @PathVariable Long id) {
    UserEntity user = userService.getUserByJwt(jwt);

    return restaurantService.addToFavorites(id, user);
  }
}
