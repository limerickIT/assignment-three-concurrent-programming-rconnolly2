package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Address;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.entitys.Wishlist;
import com.example.assignment_three_zelora.model.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private OrdersService ordersService;


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

    @GetMapping("/review/delete/{id}")
    public String deleteReview(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        var review = reviewService.getReviewById(id);

        if (review == null || !review.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            redirectAttributes.addFlashAttribute("error", "You cannot delete this review.");
            return "redirect:/reviews";
        }

        reviewService.deleteReview(id);
        redirectAttributes.addFlashAttribute("success", "Review deleted successfully.");

        return "redirect:/reviews";
    }

    @GetMapping("/addresses")
    public String addressesPage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) return "redirect:/login";

        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addressService.getAddressesByCustomer(customer));

        return "addresses";
    }

    @GetMapping("/addresses/edit/{id}")
    public String editAddressPage(@PathVariable Integer id, Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        Address address = addressService.getById(id);
        if (address == null || !address.getCustomer().getCustomerId().equals(customer.getCustomerId()))
            return "redirect:/addresses";

        model.addAttribute("address", address);
        return "edit-address";
    }

    @PostMapping("/addresses/edit/{id}")
    public String updateAddress(@PathVariable Integer id, @ModelAttribute Address updatedAddress, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        Address existing = addressService.getById(id);
        if (existing == null || !existing.getCustomer().getCustomerId().equals(customer.getCustomerId()))
            return "redirect:/addresses";

        updatedAddress.setAddressId(id);
        updatedAddress.setCustomer(customer);

        addressService.save(updatedAddress);

        redirectAttributes.addFlashAttribute("success", "Address updated successfully.");
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        Address address = addressService.getById(id);
        if (address == null || !address.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            redirectAttributes.addFlashAttribute("error", "You cannot delete this address.");
            return "redirect:/addresses";
        }

        addressService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Address removed.");
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/add")
    public String addAddressPage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        model.addAttribute("address", new Address());
        return "add-address";
    }

    @PostMapping("/addresses/add")
    public String saveAddress(@ModelAttribute Address address, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        address.setCustomer(customer);

        addressService.save(address);

        redirectAttributes.addFlashAttribute("success", "Address added successfully.");
        return "redirect:/addresses";
    }

    @GetMapping("/wishlist")
    public String wishlistPage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("wishlist", wishlistService.getWishlistByCustomer(customer.getCustomerId()));
        return "wishlist";
    }

    @GetMapping("/wishlist/delete/{id}")
    public String deleteWishlistItem(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        var item = wishlistService.getWishlistItem(id);

        if (item == null || !item.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            redirectAttributes.addFlashAttribute("error", "You cannot delete this wish.");
            return "redirect:/wishlist";
        }

        wishlistService.deleteWishlistItem(id);
        redirectAttributes.addFlashAttribute("success", "Wish removed from wishlist.");

        return "redirect:/wishlist";
    }

    @PostMapping("/wishlist/edit/{id}")
    public String editWishlist(@PathVariable Integer id, @RequestParam(required = false) String notes, @RequestParam(required = false) String wishName, HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }

        Wishlist wish = wishlistService.getWishlistItem(id);
        if (wish == null || !wish.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            redirect.addFlashAttribute("error", "You cannot edit this wish.");
            return "redirect:/wishlist";
        }

        if (notes != null) {
            wish.setNotes(notes);
        }

        if (wishName != null) {
            wish.setWishlistName(wishName);
        }

        wishlistService.updateWishlistItem(wish);

        redirect.addFlashAttribute("success", "Wish updated successfully.");
        return "redirect:/wishlist";
    }

    @GetMapping("/orders")
    public String ordersPage(@RequestParam(required = false) String status, Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        var orders = ordersService.getOrdersByCustomer(customer);

        if (status != null) {
            orders = orders.stream()
                    .filter(o -> o.getOrderStatus().equalsIgnoreCase(status))
                    .toList();
        }

        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);
        model.addAttribute("status", status);

        return "orders";
    }
}
