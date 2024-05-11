package ru.gavrilovds.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "'order'")
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private UserEntity customer;

  @ManyToOne
  @JsonIgnore
  private RestaurantEntity restaurant;

  private Long totalAmount;

  private String status;

  private OffsetDateTime createdAt;

  @ManyToOne
  private AddressEntity deliveryAddress;

  @OneToMany(mappedBy = "order")
  private List<OrderItemEntity> items = new ArrayList<>();

  private int totalItems;

  private Long totalPrice;

  // private Payment payment;
}
