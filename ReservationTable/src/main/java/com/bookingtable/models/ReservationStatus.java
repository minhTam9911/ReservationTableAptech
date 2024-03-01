package com.bookingtable.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="reservationStatus")
public class ReservationStatus {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    @Column
	    private String status;
	    @Column
	    private String reason;
	    @Column
	    private String description;
	    @OneToMany(mappedBy = "reservationStatus", cascade = CascadeType.ALL)
		private Set<Reservation> reservations = new HashSet<>();
}
