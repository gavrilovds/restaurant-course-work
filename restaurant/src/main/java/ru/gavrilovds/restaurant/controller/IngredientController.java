package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.IngredientCategoryRequest;
import ru.gavrilovds.restaurant.controller.request.IngredientItemRequest;
import ru.gavrilovds.restaurant.entity.IngredientCategoryEntity;
import ru.gavrilovds.restaurant.entity.IngredientsItemEntity;
import ru.gavrilovds.restaurant.service.IngredientsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

  private final IngredientsService ingredientsService;

  @PostMapping("/category")
  public IngredientCategoryEntity createIngredientCategory(
      @RequestBody IngredientCategoryRequest request) {
    return ingredientsService.createIngredientCategory(request.name(), request.restaurantId());
  }

  @PostMapping
  public IngredientsItemEntity createIngredientItem(
      @RequestBody IngredientItemRequest request) {
    return ingredientsService.createIngredientItem(request.restaurantId(), request.name(),
        request.categoryId());
  }

  @PutMapping("/{id}/stoke")
  public IngredientsItemEntity updateStoke(@PathVariable Long id) {
    return ingredientsService.updateStock(id);
  }

  @GetMapping("/restaurant/{id}")
  public List<IngredientsItemEntity> getRestaurantIngredient(@PathVariable Long id) {
    return ingredientsService.findRestaurantIngredients(id);
  }

  @GetMapping("/restaurant/{id}/category")
  public List<IngredientCategoryEntity> getRestaurantIngredientCategory(@PathVariable Long id) {
    return ingredientsService.findByRestaurantId(id);
  }
}
