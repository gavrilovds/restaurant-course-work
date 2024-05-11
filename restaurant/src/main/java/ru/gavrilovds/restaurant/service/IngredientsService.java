package ru.gavrilovds.restaurant.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.entity.IngredientCategoryEntity;
import ru.gavrilovds.restaurant.entity.IngredientsItemEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.exception.IngredientCategoryNotFoundException;
import ru.gavrilovds.restaurant.exception.IngredientsItemNotFoundException;
import ru.gavrilovds.restaurant.repository.IngredientCategoryRepository;
import ru.gavrilovds.restaurant.repository.IngredientItemRepository;

@Service
@RequiredArgsConstructor
public class IngredientsService {

  private final IngredientItemRepository ingredientItemRepository;
  private final IngredientCategoryRepository ingredientCategoryRepository;
  private final RestaurantService restaurantService;

  public IngredientCategoryEntity createIngredientCategory(String name, Long restaurantId) {
    RestaurantEntity restaurant = restaurantService.findRestaurantById(restaurantId);
    IngredientCategoryEntity ingredientCategory = new IngredientCategoryEntity();
    ingredientCategory.setRestaurant(restaurant);
    ingredientCategory.setName(name);
    return ingredientCategoryRepository.save(ingredientCategory);
  }

  public IngredientCategoryEntity findIngredientCategoryById(Long id) {
    Optional<IngredientCategoryEntity> ingredientCategory = ingredientCategoryRepository.findById(
        id);
    if (ingredientCategory.isEmpty()) {
      throw new IngredientCategoryNotFoundException(id);
    }
    return ingredientCategory.get();
  }

  public List<IngredientCategoryEntity> findByRestaurantId(Long restaurantId) {
    restaurantService.findRestaurantById(restaurantId);
    return ingredientCategoryRepository.findByRestaurantId(restaurantId);
  }

  public IngredientsItemEntity createIngredientItem(Long restaurantId, String ingredientName,
      Long categoryId) {
    RestaurantEntity restaurant = restaurantService.findRestaurantById(restaurantId);
    IngredientCategoryEntity ingredientCategory = findIngredientCategoryById(categoryId);
    IngredientsItemEntity item = new IngredientsItemEntity();
    item.setRestaurant(restaurant);
    item.setName(ingredientName);
    item.setCategory(ingredientCategory);
    var saved = ingredientItemRepository.save(item);
    ingredientCategory.getIngredients().add(saved);
    return saved;
  }

  public List<IngredientsItemEntity> findRestaurantIngredients(Long restaurantId) {
    restaurantService.findRestaurantById(restaurantId);
    return ingredientItemRepository.findByRestaurantId(restaurantId);
  }

  public IngredientsItemEntity updateStock(Long id) {
    Optional<IngredientsItemEntity> itemOpt = ingredientItemRepository.findById(id);
    if (itemOpt.isEmpty()) {
      throw new IngredientsItemNotFoundException(id);
    }
    var item = itemOpt.get();
    item.setInStoke(!item.isInStoke());
    return ingredientItemRepository.save(item);
  }

}
