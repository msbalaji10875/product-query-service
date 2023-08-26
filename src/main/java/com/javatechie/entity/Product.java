package com.javatechie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PRODUCT_QUERY")
public class Product {
	@jakarta.persistence.Id
	@GeneratedValue
	private Long Id;
	private String name;
	private String description;
	private Double price;

}
