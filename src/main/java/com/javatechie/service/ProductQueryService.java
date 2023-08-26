package com.javatechie.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.javatechie.dto.ProductEvent;
import com.javatechie.entity.Product;
import com.javatechie.repository.ProductRepository;

@Service
public class ProductQueryService {
	@Autowired
	private ProductRepository repository;
	
	public List<Product> getProducts() {
		return repository.findAll();
	}
	
	
	@KafkaListener(topics = "product-event-topic", groupId ="product-event-group")
	public void processProductEvent(ProductEvent prodEvent) {
		Product prodDO = prodEvent.getProduct();
	
		if(prodEvent.getEventType().endsWith("CreateProduct")) {
			repository.save(prodDO);
			
		}
		if(prodEvent.getEventType().endsWith("UpdateProduct")) {
			Product exsProduct = repository.findById(prodDO.getId()).get();
			
			exsProduct.setName(prodDO.getName());
			exsProduct.setDescription(prodDO.getDescription());
			exsProduct.setPrice(prodDO.getPrice());
			Product productDO = repository.save(exsProduct);
		}
	}

	

}
