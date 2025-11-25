package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Review;
import com.example.assignment_three_zelora.model.repos.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getReviewsByProductId(Integer productId) {
        return reviewRepository.findByProductId_ProductId(productId);
    }

    public List<Review> getReviewsByCustomerId(Integer customerId) {
        return reviewRepository.findByCustomerId_CustomerId(customerId);
    }

    public List<Review> getSpamReviews() {
        return reviewRepository.findByFlaggedAsSpam(true);
    }

    public Review updateReview(Integer id, Review updatedReview) {
        if (!reviewRepository.existsById(id)) {
            return null;
        }
        updatedReview.setReviewId(id);
        return reviewRepository.save(updatedReview);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }
}