package br.com.cotiinformatica.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.CategoryResponseDto;
import br.com.cotiinformatica.repositories.CategoryRepository;

@Service
public class CategoryDomainService {

	@Autowired CategoryRepository categoryRepository;
	@Autowired ModelMapper modelMapper;
	
	public List<CategoryResponseDto> getAll() throws Exception {
		
		List<CategoryResponseDto> response = categoryRepository.findAll()
				.stream()
				.map(category -> modelMapper.map(category, CategoryResponseDto.class))
				.collect(Collectors.toList());
		
		return response;
	}
}
