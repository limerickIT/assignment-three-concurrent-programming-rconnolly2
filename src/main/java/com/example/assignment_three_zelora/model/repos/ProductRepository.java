package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.entitys.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}