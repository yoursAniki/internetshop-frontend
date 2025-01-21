package com.example.internetshop.controller;

import com.example.internetshop.model.Product;
import com.example.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "products/view";
                })
                .orElse("error/404");
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "products/edit";
                })
                .orElse("error/404");
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}