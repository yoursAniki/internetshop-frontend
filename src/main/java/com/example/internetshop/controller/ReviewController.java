package com.example.internetshop.controller;

import com.example.internetshop.model.Review;
import com.example.internetshop.service.ProductService;
import com.example.internetshop.service.ReviewService;
import com.example.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, ProductService productService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("products", productService.getAllProducts());
        return "reviews/create";
    }

    @GetMapping("/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        return reviewService.getReviewById(id)
                .map(review -> {
                    model.addAttribute("review", review);
                    return "reviews/view";
                })
                .orElse("error/404");
    }

    @PostMapping("/new")
    public String createReview(@ModelAttribute Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return reviewService.getReviewById(id)
                .map(review -> {
                    model.addAttribute("review", review);
                    model.addAttribute("users", userService.getAllUsers());
                    model.addAttribute("products", productService.getAllProducts());
                    return "reviews/edit";
                })
                .orElse("error/404");
    }

    @PostMapping("/edit/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute Review updatedReview) {
        reviewService.updateReview(id, updatedReview);
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}