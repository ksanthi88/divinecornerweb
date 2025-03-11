package com.santhi.divinecornerweb.repository;

import com.santhi.divinecornerweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    Product findByName(String name);
    Product findById(long id);
}
