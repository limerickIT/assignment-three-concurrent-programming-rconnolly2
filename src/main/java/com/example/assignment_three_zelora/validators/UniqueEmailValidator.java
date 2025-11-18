package com.example.assignment_three_zelora.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import com.example.assignment_three_zelora.model.repos.CustomerRepository;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final CustomerRepository customerRepository;

    public UniqueEmailValidator() {
        this.customerRepository = null;
    }

    public UniqueEmailValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null || email.isBlank()) {
            return true;
        }

        if (customerRepository == null) {
            return true;
        }

        return customerRepository.getCustomersByEmail(email) == null;
    }
}
