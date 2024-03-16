package com.bookingtable.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantStatistical {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private Integer totalBookinged;
	
	private Integer totalFinished;
	
	private Integer totalCanceled;
	
	private Integer totalCustomer;
	
	private String restaurant;
	
	private Integer totalDinnerTable;
	
	private LocalDate date;
	
	
	
}
