package com.bookingtable.models;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RevenueStatistics {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private Double totalAmount;
	
	private Integer totalBookinged;
	private Integer totalInvoice;
	
	private Integer totalFinished;
	
	private Integer totalCanceled;
	
	private Integer totalCustomer;
	
	private Integer totalAgent;
	
	private Integer totalRestaurant;
	private Integer totalDinnerTable;
	private Integer totalComment;
	private Integer level1;
	private Integer level2;
	private Integer level3;
	private Integer level4;
	private Integer level5;
	
	private LocalDate date;
	
}
