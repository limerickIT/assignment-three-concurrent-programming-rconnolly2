package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByCustomerId_CustomerId(Integer customerId);

    List<Review> findByProductId_ProductId(Integer productId);

    List<Review> findByFlaggedAsSpam(Boolean flaggedAsSpam);
}
