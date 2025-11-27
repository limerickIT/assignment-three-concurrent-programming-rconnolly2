package com.example.assignment_three_zelora.controllers;

import com.example.assignment_three_zelora.model.entitys.*;
import com.example.assignment_three_zelora.model.service.OrderitemService;
import com.example.assignment_three_zelora.model.service.ProductService;
import com.example.assignment_three_zelora.model.service.OrdersService;
import com.example.assignment_three_zelora.model.service.ReferralService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private ReferralService referralService;

    @GetMapping
    public String cart(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        boolean referralDiscount = false;

        if(customer == null) {
            return "redirect:/login";
        }

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        BigDecimal total = cart.stream().map(Product::getDiscountedPrice).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        // I check if these users has any active referrals to apply 10% discount
        Referral ref = referralService.getActiveReferral();
        if (ref != null) {
            total = total.multiply(BigDecimal.valueOf(0.9));
            referralDiscount = true;
        }

        model.addAttribute("referralDiscount", referralDiscount);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("customer", customer);

        return "cart";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");

        if(customer == null) {
            return "redirect:/login";
        }

        Product p = productService.getProductById(id);
        if (p == null) {
            redirect.addFlashAttribute("error", "Product not found");
            return "redirect:/cart";
        }

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        // I check if the cart in the session exists if not I create a new list
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // I add the new product to my list of products
        cart.add(p);
        session.setAttribute("cart", cart);

        redirect.addFlashAttribute("success", p.getProductName() + " added to cart");
        return "redirect:/cart";
    }

    @GetMapping("/remove/{index}")
    public String remove(@PathVariable int index, HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");

        if(customer == null) {
            return "redirect:/login";
        }

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart != null && index >= 0 && index < cart.size()) {
            String name = cart.get(index).getProductName();
            cart.remove(index);
            redirect.addFlashAttribute("success", name + " removed from cart");
        } else {
            redirect.addFlashAttribute("error", "Invalid cart item");
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirect) {
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/login";
        }

        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirect.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        // I create first the new order
        Orders order = new Orders();
        order.setOrderDate(new Date());
        order.setOrderStatus("Processing");
        order.setCustomerId(customer);
        order.setTotalAmount(BigDecimal.ZERO);

        // I save the order first so I can reference it from the order items
        order = ordersService.createOrder(order);

        // I create order items for every product add their price to the total
        BigDecimal total = BigDecimal.valueOf(0);
        for (Product p : cart) {
            BigDecimal price = p.getDiscountedPrice();

            Orderitem item = new Orderitem();
            item.setOrderId(order);
            item.setProductId(p);
            item.setQuantity(1);
            item.setItemPrice(price);
            item.setSubtotal(price);

            total = total.add(price);

            orderitemService.createWishlistItem(item);
        }

        // I update the total after creating all the items
        // and apply discount I have referral
        Referral ref = referralService.getActiveReferral();
        if (ref != null) {
            total = total.multiply(BigDecimal.valueOf(0.9));
            ref.setStatus("Used");
            referralService.updateReferral(ref);
        }

        order.setTotalAmount(total);

        ordersService.updateOrder(order);

        session.removeAttribute("cart");
        redirect.addFlashAttribute("success", "Order placed successfully");

        return "redirect:/orders";
    }
}