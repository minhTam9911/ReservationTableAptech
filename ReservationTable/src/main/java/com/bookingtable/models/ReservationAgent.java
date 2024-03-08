package com.bookingtable.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.UUID)
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
	private String securityCode;
	@Column
	private boolean status;
	@Column
	private int totalRestaurant;
	@Column
	@CreationTimestamp
	private LocalDate created;
	@Column
	@UpdateTimestamp
	private LocalDate updated;
	@OneToMany(mappedBy = "reservationAgent") 
    private Set<Receptionist> receptionists = new HashSet<>() ;
	@OneToMany
    private Set<Restaurant> restaurants = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "role",nullable = false)
	private Role role;
	@ManyToOne
	@JoinColumn(name = "system",nullable = false)
	private System system;
}
