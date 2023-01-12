package com.ignek.ecommerce.apis.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignek.ecommerce.apis.dto.CategoryDto;
import com.ignek.ecommerce.apis.dto.ProductDto;
import com.ignek.ecommerce.apis.entity.Category;
import com.ignek.ecommerce.apis.entity.Product;
import com.ignek.ecommerce.apis.exception.ResourceNotFoundException;
import com.ignek.ecommerce.apis.repository.CategoryRepo;
import com.ignek.ecommerce.apis.repository.ProductRepo;
import com.ignek.ecommerce.apis.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProductDto> getAllProducts() {

		List<Product> products = productRepo.findAll();
		List<ProductDto> productDtos = products.stream().map(cat -> this.modelMapper.map(cat, ProductDto.class))
				.collect(Collectors.toList());
		return productDtos;

	}

	@Override
	public ProductDto getProductById(int productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));
		return this.modelMapper.map(product, ProductDto.class);

	}

	@Override
	public ProductDto createProduct(ProductDto productDto, int categoryId) {
		
		Category category =  this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		Product product = this.modelMapper.map(productDto, Product.class);
		product.setCategory(category);
		Product addedProduct = this.productRepo.save(product);
		ProductDto dto = this.modelMapper.map(addedProduct, ProductDto.class);
		dto.setCategoryName(category.getName());
		return dto 	;
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, int productId) {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));
		
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setWeight(productDto.getWeight());
		
		Product updatedProduct = this.productRepo.save(product);
		
		ProductDto dto = this.modelMapper.map(updatedProduct, ProductDto.class);
		dto.setCategoryName(product.getCategory().getName());
		return this.modelMapper.map(updatedProduct, ProductDto.class);

	}

	@Override
	public List<ProductDto> getProductByCategory(int categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category id", categoryId));

		List<Product> products = productRepo.findAllByCategory_Id(categoryId);
		List<ProductDto> productDtos = products.stream().map(product -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
		return productDtos;

	}

	@Override
	public List<ProductDto> searchProducts(String keyword) {
		
		List<Product> products = this.productRepo.findByNameContaining(keyword);
		List<ProductDto> productDtos =  products.stream().map((product)-> this.modelMapper.map(product, ProductDto.class )).collect(Collectors.toList());
		return productDtos;
		

	}

	@Override
	public void deleteProduct(int productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product id", productId));
		productRepo.delete(product);
	}

}
