package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.service.ProductService;
import com.example.assignment_three_zelora.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search")
    public String searchProducts(
            Model model,
            @RequestParam String q,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) List<String> materials,
            @RequestParam(required = false) List<String> manufacturers,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        String search = q.toLowerCase().trim();

        // I create a predicate to search products by their productName and description
        Predicate<Product> predicate = p ->
                p.getProductName().toLowerCase().contains(search) ||
                p.getDescription().toLowerCase().contains(search);

        // Then depending on the optional filters I add these conditions to the predicate
        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicate = predicate.and(p -> p.getCategoryId() != null && categoryIds.contains(p.getCategoryId().getCategoryId()));
        }

        if (materials != null && !materials.isEmpty()) {
            predicate = predicate.and(p -> materials.contains(p.getMaterial()));
        }

        if (manufacturers != null && !manufacturers.isEmpty()) {
            predicate = predicate.and(p -> manufacturers.contains(p.getManufacturer()));
        }

        // I compare the product price with maxPrice and minPrice -1 is less, 0 equal & 1 more.
        if (minPrice != null) {
            predicate = predicate.and(p -> p.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0);
        }
        if (maxPrice != null) {
            predicate = predicate.and(p -> p.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0);
        }

        List<Product> products = productService.getAllProducts()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());

        // I need to pass these attributes to the template, so I can check
        // checkboxes that have been checked and maintain the search term.
        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("materials", materials);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("q", q);

        return "search";
    }

}