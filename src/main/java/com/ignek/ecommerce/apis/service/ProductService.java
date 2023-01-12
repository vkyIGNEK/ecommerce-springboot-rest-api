package com.ignek.ecommerce.apis.service;

import java.util.List;

import com.ignek.ecommerce.apis.dto.ProductDto;

public interface ProductService {

	List<ProductDto> getAllProducts();

	ProductDto getProductById(int id);

	ProductDto createProduct(ProductDto productDto, int productId);

	ProductDto updateProduct(ProductDto productDto, int productId);

	List<ProductDto> getProductByCategory(int categoryId);

	List<ProductDto> searchProducts(String keyword);

	void deleteProduct(int productId);

}
