package ru.gavrilovds.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  List<OrderEntity> findByCustomerId(Long userId);
  List<OrderEntity> findByRestaurantId(Long restaurantId);
}
