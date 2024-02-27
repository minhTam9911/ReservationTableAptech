package com.bookingtable.models;

import java.time.LocalDate;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "reservationAgent")
public class ReservationAgent {

	@Id
	private String id;
	@Column
	private String fullName;
	@Column
	private String city;
	@Column
	private String district;
	@Column
	private String ward;
	@Column
	private String address;
	@Column
	private String email;
	@Column
	private String homePhoneNumber;
	@Column
	private String cellularPhoneNumber;
	@Column
	private String password;
	@Column
	@CreationTimestamp
	private LocalDate created;
	@Column
	@UpdateTimestamp
	private LocalDate updated;
	@ManyToOne
	@JoinColumn(name = "role",nullable = false)
	private Role role;
	
}
