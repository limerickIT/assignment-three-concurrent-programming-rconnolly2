package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Orders;
import com.example.assignment_three_zelora.model.entitys.Customer;
import com.example.assignment_three_zelora.model.repos.OrdersRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public List<Orders> getOrdersByCustomer(Customer customer) {
        return ordersRepository.getOrdersByCustomerId(customer);
    }

    public List<Orders> getOrdersByStatus(String status) {
        return ordersRepository.getOrdersByOrderStatus(status);
    }

    public Orders updateOrder(Integer id, Orders updatedOrder) {
        if (!ordersRepository.existsById(id)) {
            return null;
        }
        updatedOrder.setOrderId(id);
        return ordersRepository.save(updatedOrder);
    }

    public void deleteOrder(Integer id) {
        ordersRepository.deleteById(id);
    }
}
