package com.example.internetshop.controller;

import com.example.internetshop.model.Order;
import com.example.internetshop.service.OrderService;
import com.example.internetshop.model.OrderItem;
import com.example.internetshop.service.OrderItemService;
import com.example.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/orders/{orderId}/items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService,
                               OrderService orderService,
                               ProductService productService) { // <-- Добавлено в конструктор
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService; // <-- Инициализация поля
    }

    @GetMapping
    public String getOrderItemsForOrder(@PathVariable Long orderId, Model model) { //  <-- @PathVariable Long orderId
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("order", order);

        return "order-items/view";
    }

    // Остальные методы контроллера (getOrderItemById, showCreateForm, createOrderItem и т.д.)
    // могут остаться, если вы хотите иметь возможность просматривать, создавать, редактировать
    // и удалять OrderItem отдельно.  Если нет, их можно удалить.
    // Важно:  Маршруты для этих методов, вероятно, тоже придется скорректировать.

    @GetMapping("/{id}")
    public String getOrderItemById(@PathVariable Long id, Model model) {
        return orderItemService.getOrderItemById(id)
                .map(orderItem -> {
                    model.addAttribute("orderItem", orderItem);
                    return "order-items/view";
                })
                .orElse("error/404");
    }

    @GetMapping("/new")
    public String showCreateForm(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order); // Связываем новую позицию с заказом

        model.addAttribute("orderItem", orderItem);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orderId", orderId); // Необязательно, так как orderId доступен через order

        return "order-items/create";
    }

    @PostMapping
    public String createOrderItem(@PathVariable("orderId") Long orderId, @ModelAttribute OrderItem orderItem) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        orderItem.setOrder(order);
        order.addOrderItem(orderItem); // <-- Важно! Добавляем в обе стороны связи

        orderItemService.createOrderItem(orderItem);

        return "redirect:/orders/" + orderId + "/items";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return orderItemService.getOrderItemById(id)
                .map(orderItem -> {
                    model.addAttribute("orderItem", orderItem);
                    return "order-items/edit";
                })
                .orElse("error/404");
    }

    @PostMapping("/edit/{id}")
    public String updateOrderItem(@PathVariable("orderId") Long orderId, @PathVariable Long id, @ModelAttribute OrderItem updatedOrderItem) {
        orderItemService.updateOrderItem(id, updatedOrderItem);
        return "redirect:/orders/" + orderId + "/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable("orderId") Long orderId, @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/orders/" + orderId + "/items";
    }
}