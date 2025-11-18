package com.example.assignment_three_zelora.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.assignment_three_zelora.model.service.CategoryService;
import com.example.assignment_three_zelora.model.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("customer") != null;

        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "home";
    }
}