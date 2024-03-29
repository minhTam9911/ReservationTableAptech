package com.bookingtable.models;

import java.time.LocalDate;
import java.util.Collection;
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
@Table(name = "system")
public class System {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
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
	private String image;

	@PrePersist
	private void generateAvatarDefault() {
		this.image = "avatar.jpg";
	}

	@Column
	private boolean gender;
	@Column
	private LocalDate dateOfBirth;
	@Column
	private String securityCode;
	@Column
	private boolean status;
	@Column
	@CreationTimestamp
	private LocalDate created;
	@Column
	@UpdateTimestamp
	private LocalDate updated;
	@ManyToOne
	@JoinColumn(name = "role", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "system", cascade = CascadeType.ALL)
	private Collection<ReservationAgent> reservationAgents;
}
