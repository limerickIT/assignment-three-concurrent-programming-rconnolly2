package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.entitys.Referral;
import com.example.assignment_three_zelora.model.service.ReferralService;
import com.example.assignment_three_zelora.model.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/referrals")
    public String referrals(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        List<Referral> list = referralService.getReferrals(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("referrals", list);

        return "referrals";
    }

    @PostMapping("/invite")
    public String invite(@RequestParam String email, HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        if (referralService.emailExists(email)) {
            redirect.addFlashAttribute("error", "This email was already invited");
            return "redirect:/referrals";
        }

        Referral r = referralService.createReferral(customer, email);

        String text = "You were invited to join Zelora!\n\nUse referral code: " + r.getReferralCode();
        emailService.sendEmail(email, "Zelora Referral", text);

        redirect.addFlashAttribute("success", "Referral sent to " + email);
        return "redirect:/referrals";
    }
}