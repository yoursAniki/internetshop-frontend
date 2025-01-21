package com.example.internetshop.service.impl;

import com.example.internetshop.model.OrderItem;
import com.example.internetshop.repository.OrderItemRepository;
import com.example.internetshop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItem.setOrder(updatedOrderItem.getOrder());
                    orderItem.setProduct(updatedOrderItem.getProduct());
                    orderItem.setQuantity(updatedOrderItem.getQuantity());
                    orderItem.setUnitPrice(updatedOrderItem.getUnitPrice());
                    return orderItemRepository.save(orderItem);
                })
                .orElse(null);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId); // Используем метод репозитория
    }
}