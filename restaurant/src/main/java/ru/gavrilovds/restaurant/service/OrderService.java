package ru.gavrilovds.restaurant.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.controller.request.CreateOrderRequest;
import ru.gavrilovds.restaurant.entity.AddressEntity;
import ru.gavrilovds.restaurant.entity.CartEntity;
import ru.gavrilovds.restaurant.entity.CartItemEntity;
import ru.gavrilovds.restaurant.entity.OrderEntity;
import ru.gavrilovds.restaurant.entity.OrderItemEntity;
import ru.gavrilovds.restaurant.entity.RestaurantEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.exception.OrderNotFoundException;
import ru.gavrilovds.restaurant.repository.AddressRepository;
import ru.gavrilovds.restaurant.repository.OrderItemRepository;
import ru.gavrilovds.restaurant.repository.OrderRepository;
import ru.gavrilovds.restaurant.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;
  private final RestaurantService restaurantService;
  private final CartService cartService;

  public OrderEntity createOrder(CreateOrderRequest request, UserEntity user) {
    AddressEntity deliveryAddress = request.deliveryAddress();

    var savedAddress = addressRepository.save(deliveryAddress);
    if (!user.getAddresses().contains(savedAddress)) {
      savedAddress.setCustomer(user);
      savedAddress = addressRepository.save(savedAddress);
      user.getAddresses().add(savedAddress);
      userRepository.save(user);
    }

    RestaurantEntity restaurant = restaurantService.findRestaurantById(request.restaurantId());
    OrderEntity order = new OrderEntity();
    order.setCreatedAt(OffsetDateTime.now());
    order.setCustomer(user);
    order.setDeliveryAddress(savedAddress);
    order.setStatus("PENDING");
    order.setRestaurant(restaurant);

    CartEntity cart = cartService.findCartByUserId(user.getId());
    List<OrderItemEntity> orderItems = new ArrayList<>();
    for (CartItemEntity item : cart.getItems()) {
      OrderItemEntity orderItem = new OrderItemEntity();
      orderItem.setFood(item.getFood());
      orderItem.setIngredients(item.getIngredients());
      orderItem.setQuantity(item.getQuantity());
      orderItem.setTotalPrice(item.getTotalPrice());

      var savedOrderItem = orderItemRepository.save(orderItem);
      orderItems.add(savedOrderItem);
    }

    Long totalPrice = cartService.calculateCartTotals(cart);
    order.setItems(orderItems);
    order.setTotalPrice(totalPrice);

    var savedOrder = orderRepository.save(order);
    restaurant.getOrders().add(savedOrder);
    return savedOrder;
  }

  public OrderEntity updateOrder(Long orderId, String orderStatus) {
    OrderEntity order = findOrderById(orderId);
    if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
        || orderStatus.equals("COMPLETED")
        || orderStatus.equals("PENDING")
    ) {
      order.setStatus(orderStatus);
      return orderRepository.save(order);
    }
    return null;
  }

  public void cancelOrder(Long orderId) {
    OrderEntity order = findOrderById(orderId);
    orderRepository.delete(order);
  }

  public List<OrderEntity> getUserOrders(Long userId) {
    return orderRepository.findByCustomerId(userId);
  }

  public List<OrderEntity> getRestaurantOrders(Long restaurantId, String orderStatus) {
    return orderRepository.findByRestaurantId(restaurantId).stream()
        .filter(order -> order.getStatus().equals(orderStatus))
        .collect(Collectors.toList());
  }


  private OrderEntity findOrderById(Long orderId) {
    Optional<OrderEntity> order = orderRepository.findById(orderId);
    if (order.isEmpty()) {
      throw new OrderNotFoundException(orderId);
    }
    return order.get();
  }
}
