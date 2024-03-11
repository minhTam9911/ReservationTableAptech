package com.bookingtable.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="collection")
public class Collection {
	

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "restaurant",nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "customer",nullable = false)
	private Customer customer;

}
