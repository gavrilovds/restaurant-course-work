package ru.gavrilovds.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private Long price;

  @ManyToOne(cascade = CascadeType.ALL)
  private CategoryEntity category;

  @Column(length = 1000)
  @ElementCollection
  @CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
  private List<String> images;

  private boolean isAvailable;

  @ManyToOne
  @JsonIgnore
  private RestaurantEntity restaurant;

  private boolean isVegetarian;

  private boolean isSeasonal;

  @OneToMany(mappedBy = "food")
  @JsonIgnore
  private List<CartItemEntity> cartItems = new ArrayList<>();

  @ManyToMany
  private List<IngredientsItemEntity> ingredients = new ArrayList<>();

  private OffsetDateTime creationDate;
}
