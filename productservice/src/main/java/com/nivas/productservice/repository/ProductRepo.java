package com.nivas.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.productservice.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Object findByName(String string);

}
