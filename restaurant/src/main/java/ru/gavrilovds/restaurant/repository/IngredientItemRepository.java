package ru.gavrilovds.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.IngredientsItemEntity;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItemEntity, Long> {
  List<IngredientsItemEntity> findByRestaurantId(Long restaurantId);
}
