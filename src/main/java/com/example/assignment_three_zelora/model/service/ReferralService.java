package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Referral;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.repos.ReferralRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReferralService {

    private final ReferralRepository referralRepository;

    public ReferralService(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    public List<Referral> getReferrals(Customer customer) {
        return referralRepository.findByCustomer(customer);
    }

    public Referral createReferral(Customer customer, String email) {
        Referral r = new Referral();
        int rand = (int)(Math.random() * 900000) + 100000;
        r.setCustomer(customer);
        r.setEmail(email);
        r.setReferralCode(String.valueOf(rand));
        r.setStatus("Sent");
        r.setCreatedAt(new Date());
        return referralRepository.save(r);
    }

    public boolean emailExists(String email) {
        return referralRepository.existsByEmail(email);
    }

    public void processReferralRegistration(String referralCode) {
        Referral ref = referralRepository.getReferralByReferralCode(referralCode);

        if (referralCode != null && !ref.getStatus().equals("Used")) {
            ref.setStatus("Registered");
        }
        referralRepository.save(ref);
    }

    public Referral getActiveReferral() {
        return referralRepository.getReferralByStatus("Registered");
    }

    public void updateReferral(Referral referral) {
        referralRepository.save(referral);
    }
}