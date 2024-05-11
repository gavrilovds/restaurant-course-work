package ru.gavrilovds.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

  @Query("SELECT r FROM RestaurantEntity r "
      + "WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) "
      + "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%')) ")
  List<RestaurantEntity> findBySearchQuery(String query);

  RestaurantEntity findByOwnerId(Long userId);
}
