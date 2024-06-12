package br.com.cotiinformatica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.controllers.ProductController;
import br.com.cotiinformatica.dtos.ProductRequestDto;
import br.com.cotiinformatica.dtos.ProductResponseDto;
import br.com.cotiinformatica.handlers.GlobalExceptionHandler;
import br.com.cotiinformatica.services.ProductDomainService;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsApiApplicationTests {

	@Autowired MockMvc mockMvc;
	@MockBean ProductDomainService productDomainService;
	@InjectMocks ProductController productController;
	
	@SuppressWarnings("deprecation")
	//@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}
	
	//@Test
	public void testPost() throws Exception {
		
		ProductRequestDto request = new ProductRequestDto();
		request.setName("Test Product");
		request.setPrice(100.0);
		request.setQuantity(10);
		request.setCategoryId(1L);
		
		ProductResponseDto response = new ProductResponseDto();
		response.setName(request.getName());
		response.setPrice(request.getPrice());
		response.setQuantity(request.getQuantity());
		
		when(productDomainService.create(any(ProductRequestDto.class)))
			.thenReturn(response);
		
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(response.getName()))
				.andExpect(jsonPath("$.price").value(response.getPrice()))
				.andExpect(jsonPath("$.quantity").value(response.getQuantity()));
	}
	
	//@Test
	public void testPut() throws Exception {
		
		ProductRequestDto request = new ProductRequestDto();
		request.setName("Update Product");
		request.setPrice(150.0);
		request.setQuantity(10);
		request.setCategoryId(2L);
		
		ProductResponseDto response = new ProductResponseDto();
		response.setName(request.getName());
		response.setPrice(request.getPrice());
		response.setQuantity(request.getQuantity());
		
		when(productDomainService.update(eq(1L), any(ProductRequestDto.class)))
			.thenReturn(response);
		
		mockMvc.perform(put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(response.getName()))
				.andExpect(jsonPath("$.price").value(response.getPrice()))
				.andExpect(jsonPath("$.quantity").value(response.getQuantity()));
	}
	//@Test
	public void testDelete() throws Exception {
		
		ProductResponseDto response = new ProductResponseDto();
		
		when(productDomainService.delete(eq(1L)))
			.thenReturn(response);
		
		mockMvc.perform(delete("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	//@Test
	public void testGetAll() throws Exception {
		
		ProductResponseDto response1 = new ProductResponseDto();
		response1.setName("Product 1");
		response1.setPrice(100.0);
		
		ProductResponseDto response2 = new ProductResponseDto();
		response1.setName("Product 2");
		response1.setPrice(200.0);
		
		List<ProductResponseDto> list = Arrays.asList(response1, response2);
		
		when(productDomainService.getAll(any(Pageable.class)))
			.thenReturn(list);
		
		mockMvc.perform(get("/api/products")
			.param("page", "0")
			.param("size", "10")
			.param("sortBy", "id")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value(response1.getName()))
			.andExpect(jsonPath("$[0].price").value(response1.getPrice()))
			.andExpect(jsonPath("$[1].name").value(response2.getName()))
			.andExpect(jsonPath("$[1].price").value(response2.getPrice()));
	}
	
	//@Test
	public void testGetById() throws Exception {
		
		ProductResponseDto response = new ProductResponseDto();
		response.setName("Product 1");
		response.setPrice(100.0);
				
		when(productDomainService.getById(1L))
			.thenReturn(response);
		
		mockMvc.perform(get("/api/products/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(response.getName()))
			.andExpect(jsonPath("$.price").value(response.getPrice()));
	}
}






