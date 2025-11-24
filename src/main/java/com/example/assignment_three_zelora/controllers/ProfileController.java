package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.assignment_three_zelora.model.service.CustomerService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/account")
    public String accountPage(Model model, HttpSession session) {
        Object customer = session.getAttribute("customer");
        boolean loggedIn = customer != null;

        model.addAttribute("customer", customer);
        model.addAttribute("loggedIn", loggedIn);

        if (!loggedIn) {
            return "redirect:/login";
        }

        return "account";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        boolean loggedIn = customer != null;

        model.addAttribute("customer", customer);
        model.addAttribute("loggedIn", loggedIn);

        if (customer == null) {
            return "redirect:/login";
        }

        return "profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute Customer updatedCustomer, HttpSession session, RedirectAttributes redirectAttributes) {

        Customer currentCustomer = (Customer) session.getAttribute("customer");

        if (currentCustomer == null) {
            return "redirect:/login";
        }

        currentCustomer.setFirstName(updatedCustomer.getFirstName());
        currentCustomer.setLastName(updatedCustomer.getLastName());
        currentCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        customerService.updateCustomer(currentCustomer.getCustomerId(), currentCustomer);
        session.setAttribute("customer", currentCustomer);

        redirectAttributes.addFlashAttribute("success", "Profile saved!");

        return "redirect:/profile";
    }

    @GetMapping("/reviews")
    public String reviewsPage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("reviews", reviewService.getReviewsByCustomerId(customer.getCustomerId()));

        return "reviews";
    }
}
