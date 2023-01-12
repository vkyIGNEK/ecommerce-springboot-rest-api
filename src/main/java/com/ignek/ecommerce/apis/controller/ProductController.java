package com.ignek.ecommerce.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignek.ecommerce.apis.dto.ProductDto;
import com.ignek.ecommerce.apis.dto.response.ApiResponse;
import com.ignek.ecommerce.apis.service.ProductService;

@RestController
@RequestMapping("/ecommerce/api/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/category/{categoryId}/products")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
			@PathVariable Integer categoryId) {
		ProductDto createdProduct = productService.createProduct(productDto, categoryId);
		return new ResponseEntity<ProductDto>(createdProduct, HttpStatus.CREATED);
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
			@PathVariable Integer productId) {
		ProductDto dto = productService.updateProduct(productDto, productId);
		return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> dtos = productService.getAllProducts();

		return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Integer productId){
		ProductDto dto = productService.getProductById(productId);
		return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Integer productId){
		productService.deleteProduct(productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/products")
	public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable Integer categoryId){
		List<ProductDto> productDtos = this.productService.getProductByCategory(categoryId);
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
	
	@GetMapping("/products/search/{keyword}")
	public ResponseEntity<List<ProductDto>> searchProductByName(@PathVariable String keyword){
		List<ProductDto> productDtos = this.productService.searchProducts(keyword);
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
}
