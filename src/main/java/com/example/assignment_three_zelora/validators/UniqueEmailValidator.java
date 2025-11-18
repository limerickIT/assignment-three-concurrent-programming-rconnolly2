package com.example.assignment_three_zelora.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.assignment_three_zelora.model.repos.CustomerRepository;
import com.example.assignment_three_zelora.model.entitys.Customer;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }

        Customer existing = customerRepository.getCustomersByEmail(email);
        return existing == null;
    }
}