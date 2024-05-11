package ru.gavrilovds.restaurant.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.controller.request.CreateRestaurantRequest;
import ru.gavrilovds.restaurant.dto.RestaurantDto;
import ru.gavrilovds.restaurant.entity.AddressEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.exception.RestaurantDoesntExistException;
import ru.gavrilovds.restaurant.exception.RestaurantNotFoundException;
import ru.gavrilovds.restaurant.repository.AddressRepository;
import ru.gavrilovds.restaurant.repository.RestaurantRepository;
import ru.gavrilovds.restaurant.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

  public RestaurantEntity createRestaurant(CreateRestaurantRequest request, UserEntity user) {
    AddressEntity address = addressRepository.save(request.address());

    RestaurantEntity restaurant = new RestaurantEntity();
    restaurant.setName(request.name());
    restaurant.setDescription(request.description());
    restaurant.setCuisineType(request.cuisineType());
    restaurant.setContactInformation(request.contactInformation());
    restaurant.setImages(request.images());
    restaurant.setAddress(address);
    restaurant.setOpeningHours(request.openingHours());
    restaurant.setRegistrationDate(OffsetDateTime.now());
    restaurant.setOwner(user);

    return restaurantRepository.save(restaurant);
  }

  public RestaurantEntity updateRestaurant(Long restaurantId, CreateRestaurantRequest request) {
    RestaurantEntity restaurant = findRestaurantById(restaurantId);

    if (restaurant.getCuisineType() != null) {
      restaurant.setCuisineType(request.cuisineType());
    }
    if (restaurant.getDescription() != null) {
      restaurant.setDescription(request.description());
    }
    if (restaurant.getName() != null) {
      restaurant.setName(request.name());
    }

    return restaurantRepository.save(restaurant);
  }

  public void deleteRestaurant(Long restaurantId) {
    RestaurantEntity restaurant = findRestaurantById(restaurantId);
    restaurantRepository.delete(restaurant);
  }

  public List<RestaurantEntity> getAllRestaurants() {
    return restaurantRepository.findAll();
  }

  public List<RestaurantEntity> searchRestaurant(String query) {
    return restaurantRepository.findBySearchQuery(query);
  }

  public RestaurantEntity findRestaurantById(Long restaurantId) {
    Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);

    if (restaurant.isEmpty()) {
      throw new RestaurantDoesntExistException(restaurantId);
    }
    return restaurant.get();
  }

  public RestaurantEntity findRestaurantByUserId(Long userId) {
    RestaurantEntity restaurant = restaurantRepository.findByOwnerId(userId);
    if (restaurant == null) {
      throw new RestaurantNotFoundException(userId);
    }
    return restaurant;
  }

  public RestaurantDto addToFavorites(Long restaurantId, UserEntity user) {
    RestaurantEntity restaurant = findRestaurantById(restaurantId);

    RestaurantDto restaurantDto = new RestaurantDto(
        restaurant.getName(),
        restaurant.getImages(),
        restaurant.getDescription(),
        restaurant.getId()
    );

    if (user.getFavorites().stream().noneMatch(fav -> Objects.equals(fav.id(), restaurantId))) {
      user.getFavorites().add(restaurantDto);
    } else {
      user.getFavorites().removeIf(fav -> fav.id().equals(restaurantId));
    }

    userRepository.save(user);
    return restaurantDto;
  }

  public RestaurantEntity updateRestaurantStatus(Long restaurantId) {
    RestaurantEntity restaurant = findRestaurantById(restaurantId);
    restaurant.setOpen(!restaurant.isOpen());
    return restaurantRepository.save(restaurant);
  }
}
