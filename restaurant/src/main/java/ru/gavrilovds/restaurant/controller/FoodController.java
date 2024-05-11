package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.entity.FoodEntity;
import ru.gavrilovds.restaurant.service.FoodService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodController {

  private static final String HEADER = "Authorization";

  private final FoodService foodService;

  @GetMapping("/search")
  public List<FoodEntity> searchFood(@RequestHeader(HEADER) String jwt,
      @RequestParam String query) {
    return foodService.searchFood(query);
  }

  @GetMapping("/restaurant/{id}")
  public List<FoodEntity> getRestaurantFood(
      @RequestHeader(HEADER) String jwt,
      @PathVariable Long id,
      @RequestParam boolean isVegetarian,
      @RequestParam boolean isSeasonal,
      @RequestParam(required = false) String foodCategory) {
    return foodService.getRestaurantsFood(id, isVegetarian, isSeasonal, foodCategory);
  }
}
