package ru.gavrilovds.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gavrilovds.restaurant.dto.RestaurantDto;

@Entity
@Table(name = "'user'")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;

  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role = Role.ROLE_CUSTOMER;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<OrderEntity> orders = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"))
  private List<RestaurantDto> favorites = new ArrayList<>();

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AddressEntity> addresses = new ArrayList<>();
}
