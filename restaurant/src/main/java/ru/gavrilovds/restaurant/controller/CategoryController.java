package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.entity.CategoryEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.CategoryService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

  private static final String HEADER = "Authentication";

  private final CategoryService categoryService;
  private final UserService userService;

  @GetMapping("/restaurant")
  public List<CategoryEntity> getRestaurantCategory(@RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return categoryService.findCategoryByRestaurantId(user.getId());
  }
}
