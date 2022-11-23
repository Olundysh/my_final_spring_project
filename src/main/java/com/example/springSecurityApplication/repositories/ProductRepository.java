package com.example.springSecurityApplication.repositories;


import com.example.springSecurityApplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
