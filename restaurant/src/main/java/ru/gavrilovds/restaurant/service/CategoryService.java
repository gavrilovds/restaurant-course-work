package ru.gavrilovds.restaurant.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.entity.CategoryEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.exception.CategoryNotFoundException;
import ru.gavrilovds.restaurant.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final RestaurantService restaurantService;

  public CategoryEntity createCategory(String name, Long userId) {
    RestaurantEntity restaurant = restaurantService.findRestaurantByUserId(userId);
    CategoryEntity category = new CategoryEntity();
    category.setName(name);
    category.setRestaurant(restaurant);

    return categoryRepository.save(category);
  }

  public List<CategoryEntity> findCategoryByRestaurantId(Long userId) {
    RestaurantEntity restaurant = restaurantService.findRestaurantByUserId(userId);
    return categoryRepository.findByRestaurantId(restaurant.getId());
  }

  public CategoryEntity findCategoryById(Long categoryId) {
    Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
    if (category.isEmpty()) {
      throw new CategoryNotFoundException(categoryId);
    }
    return category.get();
  }
}
