package com.bookingtable.models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "guest")
public class Guest {
	@Id
	private String id;
	@Column
	private String fullname;
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
