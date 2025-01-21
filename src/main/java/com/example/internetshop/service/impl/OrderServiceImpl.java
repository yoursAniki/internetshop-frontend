package com.example.internetshop.service.impl;

import com.example.internetshop.model.Order;
import com.example.internetshop.repository.OrderRepository;
import com.example.internetshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setUser(updatedOrder.getUser());
                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    order.setStatus(updatedOrder.getStatus());
                    return orderRepository.save(order);
                })
                .orElse(null); // Или выбросить исключение
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}