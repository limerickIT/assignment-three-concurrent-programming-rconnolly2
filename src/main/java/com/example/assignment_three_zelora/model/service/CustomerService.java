package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.repos.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomersByEmail(email);
    }

    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        if (!customerRepository.existsById(id)) {
            return null;
        }
        updatedCustomer.setCustomerId(id);
        return customerRepository.save(updatedCustomer);
    }
}