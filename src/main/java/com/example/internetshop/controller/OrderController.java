package com.example.internetshop.controller;

import com.example.internetshop.model.Order;
import com.example.internetshop.service.OrderService;
import com.example.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        return orderService.getOrderById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "orders/view";
                })
                .orElse("error/404");
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.getAllUsers());
        return "orders/create";
    }

    @PostMapping("/new")
    public String createOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return orderService.getOrderById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("users", userService.getAllUsers());
                    return "orders/edit";
                })
                .orElse("error/404");
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order updatedOrder) {
        orderService.updateOrder(id, updatedOrder);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}