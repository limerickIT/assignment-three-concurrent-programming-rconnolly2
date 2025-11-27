package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.service.EmailService;
import com.example.assignment_three_zelora.model.service.ReferralService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.service.CustomerService;

import java.time.Instant;
import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReferralService referralService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginCustomer(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Customer foundCustomer = customerService.getCustomerByEmail(email);

        if (session.getAttribute("customer") != null) {
            return "redirect:/home";
        }

        if (foundCustomer == null || !passwordEncoder.matches(password, foundCustomer.getPassword())) {
            model.addAttribute("error", "The email or password provided are incorrect");
            return "login";
        }

        // I save current user session
        session.setAttribute("customer", foundCustomer);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid @ModelAttribute Customer customer,
                                   BindingResult result,
                                   @RequestParam(required = false)
                                   String referralCode,
                                   Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (customerService.getCustomerByEmail(customer.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setDateJoined(Date.from(Instant.now()));
        customerService.createCustomer(customer);

        if (referralCode != null && !referralCode.isBlank()) {
            referralService.processReferralRegistration(referralCode);
        }

        return "redirect:/login";
    }
}