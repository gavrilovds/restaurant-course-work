package ru.gavrilovds.restaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.controller.request.CreateFoodRequest;
import ru.gavrilovds.restaurant.entity.CategoryEntity;
import ru.gavrilovds.restaurant.entity.FoodEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.exception.FoodNotFoundException;
import ru.gavrilovds.restaurant.repository.FoodRepository;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;

  public FoodEntity createFood(CreateFoodRequest request, CategoryEntity category,
      RestaurantEntity restaurant) {

    FoodEntity food = new FoodEntity();
    food.setCategory(category);
    food.setDescription(request.description());
    food.setImages(request.images());
    food.setIngredients(request.ingredients());
    food.setRestaurant(restaurant);
    food.setPrice(request.price());
    food.setSeasonal(request.isSeasonal());
    food.setVegetarian(request.isVegetarian());

    FoodEntity savedFood = foodRepository.save(food);
    restaurant.getFoods().add(savedFood);

    return savedFood;
  }

  public void deleteFood(Long foodId) {
    FoodEntity food = findFoodById(foodId);
    food.setRestaurant(null);
    foodRepository.save(food);
  }

  public List<FoodEntity> getRestaurantsFood(Long restaurantId, boolean isVegetarian,
      boolean isSeasonal, String foodCategory) {

    List<FoodEntity> foods = foodRepository.findByRestaurantId(restaurantId);

    return foods.stream()
        .filter(food -> food.isVegetarian() == isVegetarian)
        .filter(FoodEntity::isAvailable)
        .filter(food -> food.isSeasonal() == isSeasonal)
        .filter(food -> {
          if (foodCategory != null && !foodCategory.isBlank()) {
            return food.getCategory().getName().equals(foodCategory);
          }
          return true;
        })
        .collect(Collectors.toList());
  }

  public List<FoodEntity> searchFood(String query) {
    return foodRepository.searchFood(query);
  }

  public FoodEntity findFoodById(Long foodId) {
    Optional<FoodEntity> food = foodRepository.findById(foodId);
    if (food.isEmpty()) {
      throw new FoodNotFoundException(foodId);
    }
    return food.get();
  }

  public FoodEntity updateAvailabilityStatus(Long foodId) {
    FoodEntity food = findFoodById(foodId);
    food.setAvailable(!food.isAvailable());
    return foodRepository.save(food);
  }
}
