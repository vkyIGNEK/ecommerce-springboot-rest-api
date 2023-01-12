package com.ignek.ecommerce.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ignek.ecommerce.apis.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
