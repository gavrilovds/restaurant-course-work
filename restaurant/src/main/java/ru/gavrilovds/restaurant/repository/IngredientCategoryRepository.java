package ru.gavrilovds.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.IngredientCategoryEntity;

@Repository
public interface IngredientCategoryRepository extends
    JpaRepository<IngredientCategoryEntity, Long> {

  List<IngredientCategoryEntity> findByRestaurantId(Long restaurantId);
}
