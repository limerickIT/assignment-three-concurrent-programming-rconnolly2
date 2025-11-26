package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Orderitem;
import com.example.assignment_three_zelora.model.repos.OrderitemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderitemService {

    private final OrderitemRepository orderitemRepository;

    public OrderitemService(OrderitemRepository orderitemRepository) {
        this.orderitemRepository = orderitemRepository;
    }

    public void createWishlistItem(Orderitem orderitem) {
        orderitemRepository.save(orderitem);
    }
}