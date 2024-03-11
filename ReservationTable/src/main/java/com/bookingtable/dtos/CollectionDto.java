package com.bookingtable.dtos;

import com.bookingtable.models.Customer;
import com.bookingtable.models.Restaurant;

import groovy.transform.builder.Builder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
	
	
	private Integer id;
	
	
	private Restaurant restaurant;

	private Customer customer;

}

