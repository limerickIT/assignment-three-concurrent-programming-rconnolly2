package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Address;
import com.example.assignment_three_zelora.model.entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCustomer(Customer customer);
}