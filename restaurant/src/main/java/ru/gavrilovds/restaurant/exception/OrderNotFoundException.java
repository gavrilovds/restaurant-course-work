package ru.gavrilovds.restaurant.exception;

public class OrderNotFoundException extends BaseException {

  public OrderNotFoundException(Long orderId) {
    super("Order with id %d is not found".formatted(orderId));
  }
}
