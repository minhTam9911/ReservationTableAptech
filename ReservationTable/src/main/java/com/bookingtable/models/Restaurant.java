package com.bookingtable.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookingtable.helpers.GenerateCode;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "restaurant")
public class Restaurant {
    @Id
    private String id;
    @PrePersist
    private void generateId() {
        this.id = GenerateCode.GenerateRestaurent();
    }
    @Column
    private String name;
    @Column
    private String mainPhoneNumber;
    @Column
    private String faxNumber;
    @Column
    private String tollFreeNumber;
    @Column
    private String companyMail;
    @Column
    private String website;
    @Column
    private String city;
    @Column
    private String district;
    @Column
    private String ward;
    @Column
	@CreationTimestamp
	private LocalDate created;
	@Column
	@UpdateTimestamp
	private LocalDate updated;
    @Column
    private String status;
	@OneToOne
	@JoinColumn(name = "reservationAgent_id")
	private ReservationAgent reservationAgent;
    @Column
    private String address;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Collection<DinnerTable> dinnerTables = new ArrayList<>();
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Collection<Image> images = new ArrayList<>();
    

}


