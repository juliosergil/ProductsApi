package br.com.cotiinformatica.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ProductRequestDto;
import br.com.cotiinformatica.dtos.ProductResponseDto;
import br.com.cotiinformatica.entities.Category;
import br.com.cotiinformatica.entities.Product;
import br.com.cotiinformatica.repositories.CategoryRepository;
import br.com.cotiinformatica.repositories.ProductRepository;

@Service
public class ProductDomainService {

	@Autowired ProductRepository productRepository;
	@Autowired CategoryRepository categoryRepository;
	@Autowired ModelMapper modelMapper;
	
	public ProductResponseDto create(ProductRequestDto request) throws Exception {
		
		Product product = modelMapper.map(request, Product.class);
		Category category = categoryRepository
								.findById(request.getCategoryId())
								.orElseThrow(() -> new Exception("Category not found!"));
		
		product.setCategory(category);
		productRepository.save(product);
		
		return modelMapper.map(product, ProductResponseDto.class);
	}
	
	public ProductResponseDto update(Long id, ProductRequestDto request) throws Exception {
		
		Product product = productRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Product not found!"));
		
		Category category = categoryRepository
				.findById(request.getCategoryId())
				.orElseThrow(() -> new Exception("Category not found!"));
		
		product.setName(request.getName());
		product.setPrice(BigDecimal.valueOf(request.getPrice()));
		product.setQuantity(request.getQuantity());
		product.setCategory(category);
		
		productRepository.save(product);
		
		return modelMapper.map(product, ProductResponseDto.class);
	}
	
	public ProductResponseDto delete(Long id) throws Exception {
	
		Product product = productRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Product not found!"));
		
		productRepository.delete(product);
		
		return modelMapper.map(product, ProductResponseDto.class);
	}	
	
	public List<ProductResponseDto> getAll(Pageable pageable) throws Exception {
		
		Page<Product> products = productRepository.findAll(pageable);
		
		List<ProductResponseDto> response = products
				.stream()
				.map(product -> modelMapper.map(product, ProductResponseDto.class))
				.collect(Collectors.toList());
				
		return response;
	}
	
	public ProductResponseDto getById(Long id) throws Exception {
		
		Product product = productRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Product not found!"));
		
		return modelMapper.map(product, ProductResponseDto.class);
	}
	
}
