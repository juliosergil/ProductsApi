package br.com.cotiinformatica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CategoryResponseDto;
import br.com.cotiinformatica.services.CategoryDomainService;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

	@Autowired CategoryDomainService categoryDomainService;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponseDto>> getAll() throws Exception {
		return ResponseEntity.status(200).body(categoryDomainService.getAll());
	}
}
