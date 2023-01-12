package com.ignek.ecommerce.apis.service;

import java.util.List;

import com.ignek.ecommerce.apis.dto.CategoryDto;

public interface CategoryService {
	
	// Create
		CategoryDto createCategory(CategoryDto categoryDto);
		
		// update
		CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
		
		// delete
		void deleteCategory(Integer categoryId);
		
		// get by Id
		CategoryDto getCategory(Integer categoryId);
		
		// get All
		List<CategoryDto> getAllCategories();

}
