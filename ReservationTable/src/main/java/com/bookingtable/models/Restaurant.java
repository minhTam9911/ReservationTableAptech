package com.bookingtable.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "restaurant")
public class Restaurant {
    @Id
    private String id;
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


