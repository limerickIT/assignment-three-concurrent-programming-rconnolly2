package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Wishlist;
import com.example.assignment_three_zelora.model.repos.WishlistRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlistByCustomer(Integer customerId) {
        return wishlistRepository.findByCustomerId_CustomerId(customerId);
    }

    public Wishlist getWishlistItem(Integer id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public void deleteWishlistItem(Integer id) {
        wishlistRepository.deleteById(id);
    }

    public void updateWishlistItem(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public void createWishlistItem(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }
}