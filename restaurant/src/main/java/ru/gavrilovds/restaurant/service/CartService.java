package ru.gavrilovds.restaurant.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.controller.request.AddCartItemRequest;
import ru.gavrilovds.restaurant.entity.CartEntity;
import ru.gavrilovds.restaurant.entity.CartItemEntity;
import ru.gavrilovds.restaurant.entity.FoodEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.exception.CartItemIsNotFoundException;
import ru.gavrilovds.restaurant.exception.CartNotFoundException;
import ru.gavrilovds.restaurant.repository.CartItemRepository;
import ru.gavrilovds.restaurant.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final FoodService foodService;
  private final UserService userService;

  public CartItemEntity addItemToCart(AddCartItemRequest request, String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    FoodEntity food = foodService.findFoodById(request.foodId());
    CartEntity cart = cartRepository.findByCustomerId(user.getId());

    for (CartItemEntity cartItem : cart.getItems()) {
      if (cartItem.getFood().equals(food)) {
        int newQuantity = cartItem.getQuantity() + request.quantity();
        return updateCartItemQuantity(cartItem.getId(), newQuantity);
      }
    }

    CartItemEntity cartItem = new CartItemEntity();
    cartItem.setQuantity(request.quantity());
    cartItem.setFood(food);
    cartItem.setCart(cart);
    cartItem.setIngredients(request.ingredients());
    cartItem.setTotalPrice(request.quantity() * food.getPrice());

    var saved = cartItemRepository.save(cartItem);
    cart.getItems().add(saved);

    return saved;
  }

  public CartItemEntity updateCartItemQuantity(Long cartItemId, int quantity) {
    Optional<CartItemEntity> cartItemOpt = cartItemRepository.findById(cartItemId);
    if (cartItemOpt.isEmpty()) {
      throw new CartItemIsNotFoundException(cartItemId);
    }

    var cartItem = cartItemOpt.get();

    cartItem.setQuantity(quantity);
    cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

    return cartItemRepository.save(cartItem);
  }

  public CartEntity removeItemFromCart(Long cartItemId, String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    CartEntity cart = cartRepository.findByCustomerId(user.getId());
    cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
    return cartRepository.save(cart);
  }

  public Long calculateCartTotals(CartEntity cart) {
    return cart.getItems().stream()
        .mapToLong(item -> item.getFood().getPrice() * item.getQuantity())
        .sum();
  }

  public CartEntity findCartById(Long id) {
    Optional<CartEntity> cart = cartRepository.findById(id);
    if (cart.isEmpty()) {
      throw new CartNotFoundException(id);
    }
    return cart.get();
  }

  public CartEntity findCartByUserId(Long userId) {
    var cart = cartRepository.findByCustomerId(userId);
    cart.setTotal(calculateCartTotals(cart));
    return cart;
  }

  public CartEntity clearCart(Long userId) {
    CartEntity cart = findCartByUserId(userId);
    cart.getItems().clear();
    return cartRepository.save(cart);
  }
}
