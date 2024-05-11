package ru.gavrilovds.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilovds.restaurant.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

  CartEntity findByCustomerId(Long userId);
}
