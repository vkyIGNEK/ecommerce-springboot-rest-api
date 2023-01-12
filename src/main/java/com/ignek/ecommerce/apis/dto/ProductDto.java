package com.ignek.ecommerce.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

	private Long id;

	private String name;

	private String categoryName;

	private double price;

	private double weight;

	private String description;

	private String imageName;

}
