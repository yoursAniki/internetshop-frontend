package com.example.internetshop.service;

import com.example.internetshop.model.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    Optional<OrderItem> getOrderItemById(Long id);

    OrderItem createOrderItem(OrderItem orderItem);

    OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem);

    void deleteOrderItem(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId); // Новый метод
}