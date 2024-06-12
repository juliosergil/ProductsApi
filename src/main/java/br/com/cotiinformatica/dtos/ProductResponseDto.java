package br.com.cotiinformatica.dtos;

import lombok.Data;

@Data
public class ProductResponseDto {

	private Long id;
	private String name;
	private Double price;
	private Integer quantity;
	private CategoryResponseDto category;
}
