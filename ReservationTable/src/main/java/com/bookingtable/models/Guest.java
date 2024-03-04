package com.bookingtable.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "guest")
public class Guest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@Column
	private String fullName;
	@Column
	private String phoneNumber;
	@Column
	private String address;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private boolean gender;
	@Column
	private LocalDate dateOfBirth;
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
