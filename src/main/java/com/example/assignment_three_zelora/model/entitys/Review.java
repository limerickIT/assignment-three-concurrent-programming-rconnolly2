package com.example.assignment_three_zelora.model.entitys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "reviews")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Lob
    @Column(name = "review_text")
    private String reviewText;
    
    @Column(name = "review_date")
    @Temporal(TemporalType.DATE)
    private Date reviewDate;
    
    @Column(name = "flagged_as_spam")
    private Boolean flaggedAsSpam;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customerId;
    
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product productId;

    public Review(Integer reviewId, Integer rating, String reviewText, Date reviewDate, Boolean flaggedAsSpam, Customer customerId, Product productId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.flaggedAsSpam = flaggedAsSpam;
        this.customerId = customerId;
        this.productId = productId;
    }

    public Review() {
    }

    public Integer getReviewId() {
        return this.reviewId;
    }

    public Integer getRating() {
        return this.rating;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public Date getReviewDate() {
        return this.reviewDate;
    }

    public Boolean getFlaggedAsSpam() {
        return this.flaggedAsSpam;
    }

    public Customer getCustomerId() {
        return this.customerId;
    }

    public Product getProductId() {
        return this.productId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setFlaggedAsSpam(Boolean flaggedAsSpam) {
        this.flaggedAsSpam = flaggedAsSpam;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String toString() {
        return "Review(reviewId=" + this.getReviewId() + ", rating=" + this.getRating() + ", reviewText=" + this.getReviewText() + ", reviewDate=" + this.getReviewDate() + ", flaggedAsSpam=" + this.getFlaggedAsSpam() + ")";
    }
}