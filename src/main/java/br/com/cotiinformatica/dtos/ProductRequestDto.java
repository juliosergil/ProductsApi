package br.com.cotiinformatica.dtos;

import lombok.Data;

@Data
public class ProductRequestDto {

	private String name;
	private Double price;
	private Integer quantity;
	private Long categoryId;
}
