package com.example.internetshop.service;

import com.example.internetshop.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Long id, Order updatedOrder);

    void deleteOrder(Long id);
}