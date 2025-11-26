package com.example.assignment_three_zelora.model.entitys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "wishlist")
public class Wishlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Integer wishlistId;

    @Column(name = "added_date")
    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @Column(name = "wishlist_name")
    private String wishlistName;

    @Lob
    @Column(name = "notes")
    private String notes;

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customerId;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product productId;


    public Wishlist(Integer wishlistId, Date addedDate, String wishlistName, String notes, Customer customerId, Product productId) {
        this.wishlistId = wishlistId;
        this.addedDate = addedDate;
        this.wishlistName = wishlistName;
        this.notes = notes;
        this.customerId = customerId;
        this.productId = productId;
    }

    public Wishlist() {
    }

    public Integer getWishlistId() {
        return this.wishlistId;
    }

    public Date getAddedDate() {
        return this.addedDate;
    }

    public String getWishlistName() {
        return this.wishlistName;
    }

    public String getNotes() {
        return this.notes;
    }

    public Customer getCustomerId() {
        return this.customerId;
    }

    public Product getProductId() {
        return this.productId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String toString() {
        return "Wishlist(wishlistId=" + this.getWishlistId() + ", addedDate=" + this.getAddedDate() + ", wishlistName=" + this.getWishlistName() + ", notes=" + this.getNotes() + ")";
    }
}
