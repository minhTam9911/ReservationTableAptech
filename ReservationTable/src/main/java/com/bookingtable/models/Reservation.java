package com.bookingtable.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;

import com.bookingtable.helpers.GenerateCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private String id;

    @PrePersist
    private void generateId() {
        this.id = GenerateCode.GenerateReservation();
    }
	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	@OneToOne
	@JoinColumn(name = "guest_id")
	private Guest guest;
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private Collection<DinnerTable> dinnerTable;
	@Column
	@CreationTimestamp
	private LocalDateTime created;
	@Column
	private int tableCount;
	@Column
	private int numberOfPepole;
	@Column
	private int floor;
	@Column
	private LocalDate bookingDate;
	@Column
	private LocalTime bookingTime;
	@ManyToOne
    @JoinColumn(name = "reservationStatus_id")
    private ReservationStatus reservationStatus;
}
