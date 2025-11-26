package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Wishlist;
import com.example.assignment_three_zelora.model.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.assignment_three_zelora.model.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @GetMapping("/wishlist/add/{id}")
    public String newWish(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");
        Product product = productService.getProductById(id);

        if (customer == null) {
            return "redirect:/login";
        }

        if (product == null) {
            redirect.addFlashAttribute("error", "This product doesn't exist .");
            return "redirect:/wishlist";
        }

        // I create the new wish and send it to the service
        Wishlist wish = new Wishlist();
        wish.setCustomerId(customer);
        wish.setProductId(product);
        wish.setAddedDate(new Date());
        wishlistService.createWishlistItem(wish);

        redirect.addFlashAttribute("success", "Wish added to wishlist.");
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/delete/{id}")
    public String deleteWishlistItem(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

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
    public String editWishlist(@PathVariable Integer id, @RequestParam(required = false) String notes,
                               @RequestParam(required = false) String wishName, HttpSession session, RedirectAttributes redirect) {

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) return "redirect:/login";

        Wishlist wish = wishlistService.getWishlistItem(id);
        if (wish == null || !wish.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            redirect.addFlashAttribute("error", "You cannot edit this wish.");
            return "redirect:/wishlist";
        }

        if (notes != null) wish.setNotes(notes);
        if (wishName != null) wish.setWishlistName(wishName);

        wishlistService.updateWishlistItem(wish);
        redirect.addFlashAttribute("success", "Wish updated successfully.");
        return "redirect:/wishlist";
    }
}