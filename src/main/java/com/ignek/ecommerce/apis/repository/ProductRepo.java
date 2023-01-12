package com.ignek.ecommerce.apis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ignek.ecommerce.apis.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	List<Product> findAllByCategory_Id(int id);
	
	List<Product> findByNameContaining(String name);

}
