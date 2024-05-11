package ru.gavrilovds.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilovds.restaurant.entity.OrderEntity;
import ru.gavrilovds.restaurant.service.OrderService;
import ru.gavrilovds.restaurant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminOrderController {

  private static final String HEADER = "Authorization";

  private final OrderService orderService;
  private final UserService userService;


  @GetMapping("/orders/restaurant/{id}")
  public List<OrderEntity> getOrderHistory(@RequestHeader(HEADER) String jwt, @PathVariable Long id,
      @RequestParam String orderStatus) {
    return orderService.getRestaurantOrders(id, orderStatus);
  }

  @PutMapping("/orders/{orderId}/{orderStatus}")
  public OrderEntity updateOrderStatus(@RequestHeader(HEADER) String jwt,
      @PathVariable Long orderId,
      @PathVariable String orderStatus) {
    return orderService.updateOrder(orderId, orderStatus);
  }

}
