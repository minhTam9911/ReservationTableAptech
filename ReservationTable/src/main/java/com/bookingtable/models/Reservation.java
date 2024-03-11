package com.bookingtable.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.bookingtable.dtos.CustomerDto;
import org.hibernate.annotations.CreationTimestamp;

import com.bookingtable.helpers.GenerateCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	@ManyToOne
	@JoinColumn(name = "dinnerTable_id")
	private DinnerTable dinnerTable;
	@Column
	@CreationTimestamp
	private LocalDateTime created;
	@Column
	private int numberOfPeople;
	@Column
	private int quantity;
	@Column
	private LocalDate bookingDate;
	@Column
	private LocalTime bookingTime;
	@ManyToOne
    @JoinColumn(name = "reservationStatus", nullable = false)
    private ReservationStatus reservationStatus;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
