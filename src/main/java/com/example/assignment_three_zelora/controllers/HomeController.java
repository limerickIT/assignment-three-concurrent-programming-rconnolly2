package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.assignment_three_zelora.model.service.CategoryService;
import com.example.assignment_three_zelora.model.service.ProductService;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {
        List<Product> products = productService.getAllProducts();
        boolean loggedIn = session.getAttribute("customer") != null;

        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("new_products", products.stream()
                .sorted(Comparator.comparing(Product::getReleaseDate).reversed())
                .limit(10)
                .toList());
        model.addAttribute("discounted_products", products.stream()
                .sorted(Comparator.comparing((Product p) -> p.getPrice().subtract(p.getDiscountedPrice())).reversed())
                .limit(10).toList()
        );
        model.addAttribute("categories", categoryService.getAllCategories());

        return "home";
    }
}