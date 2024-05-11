package ru.gavrilovds.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.CreateFoodRequest;
import ru.gavrilovds.restaurant.controller.response.MessageResponse;
import ru.gavrilovds.restaurant.entity.FoodEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.service.FoodService;
import ru.gavrilovds.restaurant.service.RestaurantService;
import ru.gavrilovds.restaurant.service.UserService;

@RequestMapping("/api/admin/foods")
@RestController
@RequiredArgsConstructor
public class AdminFoodController {

  private static final String HEADER = "Authorization";

  private final FoodService foodService;
  private final UserService userService;
  private final RestaurantService restaurantService;

  @PostMapping
  public FoodEntity createFood(@RequestHeader(HEADER) String jwt,
      @RequestBody CreateFoodRequest request) {
    RestaurantEntity restaurant = restaurantService.findRestaurantById(request.restaurantId());
    return foodService.createFood(request, request.category(), restaurant);
  }

  @DeleteMapping("/{id}")
  public MessageResponse deleteFood(@RequestHeader(HEADER) String jwt, @PathVariable Long id) {
    foodService.deleteFood(id);
    return new MessageResponse("Food was deleted successfully");
  }

  @PutMapping("/{id}")
  public FoodEntity updateFoodAvailabilityStatus(@RequestHeader(HEADER) String jwt,
      @PathVariable Long id
  ) {
    return foodService.updateAvailabilityStatus(id);
  }

}
