package ru.gavrilovds.restaurant.controller;

import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.entity.CategoryEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.CategoryService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/categories")
public class AdminCategoryController {

  private static final String HEADER = "Authorization";

  private final CategoryService categoryService;
  private final UserService userService;

  @PostMapping
  public CategoryEntity createCategory(@RequestHeader(HEADER) String jwt,
      @RequestBody CategoryEntity category) {
    UserEntity user = userService.getUserByJwt(jwt);
    return categoryService.createCategory(category.getName(), user.getId());
  }
}
