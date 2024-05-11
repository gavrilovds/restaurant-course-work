package ru.gavrilovds.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ingredients_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IngredientsItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  private IngredientCategoryEntity category;

  @ManyToOne
  @JsonIgnore
  private RestaurantEntity restaurant;

  private boolean isInStoke = true;
}
