package ru.gavrilovds.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.AddCartItemRequest;
import ru.gavrilovds.restaurant.controller.request.UpdateCartItemRequest;
import ru.gavrilovds.restaurant.entity.CartEntity;
import ru.gavrilovds.restaurant.entity.CartItemEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.CartService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

  public static final String HEADER = "Authorization";
  private final CartService cartService;
  private final UserService userService;

  @PutMapping("/cart/add")
  public CartItemEntity addItemToCart(@RequestHeader(HEADER) String jwt,
      @RequestBody AddCartItemRequest request) {
    return cartService.addItemToCart(request, jwt);
  }

  @PutMapping("/cart-item/update")
  public CartItemEntity updateCartItemQuantity(@RequestBody UpdateCartItemRequest request) {
    return cartService.updateCartItemQuantity(request.cartId(), request.quantity());
  }

  @PutMapping("/cart-item/{id}/remove")
  public CartEntity removeCartItem(@RequestHeader(HEADER) String jwt,
      @PathVariable Long id) {
    return cartService.removeItemFromCart(id, jwt);
  }

  @PutMapping("/cart/clear")
  public CartEntity clearCart(@RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return cartService.clearCart(user.getId());
  }

  @GetMapping("/cart")
  public CartEntity findUserCart(@RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return cartService.findCartByUserId(user.getId());
  }
}
