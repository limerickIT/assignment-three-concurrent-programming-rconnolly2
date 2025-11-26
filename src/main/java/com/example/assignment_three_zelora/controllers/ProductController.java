package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Review;
import com.example.assignment_three_zelora.model.entitys.Inventory;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.service.ProductService;
import com.example.assignment_three_zelora.model.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/{id}")
    public String productPage(@PathVariable Integer id, Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404";
        }

        List<Review> reviews = reviewService.getReviewsByProductId(id);
        // I filter the reviews with a rating larger than 2
        List<Review> filteredReviews = reviews.stream()
                .filter(r -> r.getRating() >= 3)
                .collect(Collectors.toList());

        double averageRating = reviews.isEmpty() ? 0 : reviews.stream().mapToInt(Review::getRating).average().orElse(0);

        List<Inventory> inventories = product.getInventoryList();
        String stockStatus;
        // I get the availableQty & reorderPoint from all the suppliers
        int availableQty = inventories.stream().mapToInt(i -> i.getQuantityInStock() - i.getQuantityReserved()).sum();
        int reorderPoint = (int) inventories.stream().mapToInt(Inventory::getQuantityReserved).average().orElse(0);

        if (availableQty <= 0) {
            stockStatus = "out";
        } else if (availableQty <= reorderPoint) {
            stockStatus = "low";
        } else {
            stockStatus = "in";
        }

        model.addAttribute("product", product);
        model.addAttribute("category", product.getCategoryId());
        model.addAttribute("reviews", filteredReviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("availableQty", availableQty);
        model.addAttribute("stockStatus", stockStatus);
        model.addAttribute("customer", customer);

        return "product";
    }

    @PostMapping("/product/review/{id}")
    public String submitReview(@PathVariable Integer id, @RequestParam Integer rating, @RequestParam String reviewText, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Product product = productService.getProductById(id);
        Review review = new Review();

        if (customer == null) {
            return "redirect:/login";
        }

        if (product == null) {
            return "error/404";
        }

        // I create the review and send it to the service
        review.setProductId(product);
        review.setCustomerId(customer);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setFlaggedAsSpam(false);
        review.setReviewDate(new java.util.Date());

        reviewService.createReview(review);

        return "redirect:/product/" + id;
    }
}