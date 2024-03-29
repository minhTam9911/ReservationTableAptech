package com.bookingtable.models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "rate")
public class Rate {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private double point;
	@Column(columnDefinition = "VARCHAR")	
	private String comment;
	@Column
	@CreationTimestamp
	private LocalDate created;
	@Column
	private boolean status;
	@OneToOne
	@JoinColumn(name = "reservation") 
	private Reservation reservation;
	@ManyToOne
	@JoinColumn(name = "customer",nullable = false)
	private Customer customer;
}
