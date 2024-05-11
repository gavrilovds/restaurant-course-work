package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.controller.request.CreateOrderRequest;
import ru.gavrilovds.restaurant.entity.OrderEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.service.OrderService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

  private static final String HEADER = "Authorization";

  private final OrderService orderService;
  private final UserService userService;

  @PostMapping("/orders")
  public OrderEntity createOrder(@RequestBody CreateOrderRequest request,
      @RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return orderService.createOrder(request, user);
  }

  @GetMapping("/orders/user")
  public List<OrderEntity> getOrderHistory(@RequestHeader(HEADER) String jwt) {
    UserEntity user = userService.getUserByJwt(jwt);
    return orderService.getUserOrders(user.getId());
  }

}
