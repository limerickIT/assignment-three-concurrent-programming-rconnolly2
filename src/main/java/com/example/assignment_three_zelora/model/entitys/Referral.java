package com.example.assignment_three_zelora.model.entitys;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "referrals")
public class Referral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referral_id")
    private Integer referralId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email")
    private String email;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Referral() {
    }

    public Integer getReferralId() {
        return referralId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getEmail() {
        return email;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}