package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Referral;
import com.example.assignment_three_zelora.model.entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferralRepository extends JpaRepository<Referral, Integer> {
    List<Referral> findByCustomer(Customer customer);
    boolean existsByEmail(String email);
    Referral getReferralByReferralCode(String referralCode);
    Referral getReferralByStatus(String status);
}