package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<Orderitem, Integer> {
}

