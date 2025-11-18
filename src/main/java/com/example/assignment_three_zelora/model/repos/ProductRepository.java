package com.example.assignment_three_zelora.repository;

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

    List<Product> findByProductNameContainingIgnoreCase(String name);

    List<Product> findByCategoryId(Category category);

    List<Product> findBySupplierId(Supplier supplier);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByDiscountedPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByReleaseDateAfter(Date date);

    List<Product> findByDescriptionContainingIgnoreCase(String keyword);

    List<Product> findBySustainabilityRatingGreaterThanEqual(Integer rating);

    List<Product> findByColourContainingIgnoreCase(String colour);

    List<Product> findByMaterialContainingIgnoreCase(String material);
}