package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Orders;
import com.example.assignment_three_zelora.model.entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> getOrdersByCustomerId(Customer customer);
    List<Orders> getOrdersByOrderStatus(String status);
}