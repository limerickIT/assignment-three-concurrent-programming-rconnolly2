package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Address;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.repos.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public List<Address> getAddressesByCustomer(Customer c) {
        return repo.findByCustomer(c);
    }

    public Address save(Address a) {
        return repo.save(a);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Address getById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
