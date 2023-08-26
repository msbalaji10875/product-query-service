package com.javatechie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.dto.ProductEvent;
import com.javatechie.entity.Product;
import com.javatechie.repository.ProductRepository;
import com.javatechie.service.ProductQueryService;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
	@Autowired
	private ProductRepository prodRepository;
	
	@Autowired
	private ProductQueryService productQueryService;
	
	@GetMapping
	public List<Product> fetchAllProducts() {
		return productQueryService.getProducts();
	}
	
	@KafkaListener(topics ="product-event-topic", groupId="product-event-group")
	public void processProductEvent(ProductEvent prodEvent) {
		Product productDO = prodEvent.getProduct();
		
		if(prodEvent.getEventType().equals("CreateEvent")) {
			prodRepository.save(productDO);
		}
		if(prodEvent.getEventType().equals("UpdateEvent")) {
			Product exsProd = prodRepository.findById(productDO.getId()).get();
			exsProd.setName(productDO.getName());
			exsProd.setDescription(productDO.getDescription());
			exsProd.setPrice(productDO.getPrice());
			prodRepository.save(exsProd);
		}
	}
		

}
