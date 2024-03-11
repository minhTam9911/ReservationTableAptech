package com.bookingtable.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "invoice")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@OneToOne
    @JoinColumn(name = "reservation")
    private Reservation reservation;
}
