package com.harsha_SMS.product_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha_SMS.product_service.dto.ProductRequest;
import com.harsha_SMS.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;  // Autowired MockMvc

	@Autowired
	private ObjectMapper objectMapper;  // Autowired ObjectMapper

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setup() {
		productRepository.deleteAll();  // Clear the repository before each test
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest=getProductRequest();
		String productRequestString= objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)
		).andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("iphone 16")
				.description("newwest phone")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
