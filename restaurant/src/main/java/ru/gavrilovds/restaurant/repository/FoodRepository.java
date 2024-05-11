package ru.gavrilovds.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.FoodEntity;
@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

  List<FoodEntity> findByRestaurantId(Long restaurantId);

  @Query("SELECT f FROM FoodEntity f WHERE f.name LIKE %:query% OR f.category.name LIKE %:query% ")
  List<FoodEntity> searchFood(@Param("query") String query);
}
