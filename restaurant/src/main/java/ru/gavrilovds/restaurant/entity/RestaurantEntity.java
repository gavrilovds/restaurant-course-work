package ru.gavrilovds.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private UserEntity owner;

  private String name;

  private String description;

  private String cuisineType;

  @OneToOne
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  @Embedded
  private ContactInformation contactInformation;

  private String openingHours;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<OrderEntity> orders = new ArrayList<>();

  @ElementCollection
  @Column(length = 1000)
  @CollectionTable(name = "restaurant_images", joinColumns = @JoinColumn(name = "restaurant_id"))
  private List<String> images;

  private OffsetDateTime registrationDate;

  private boolean isOpen;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<FoodEntity> foods = new ArrayList<>();
}
