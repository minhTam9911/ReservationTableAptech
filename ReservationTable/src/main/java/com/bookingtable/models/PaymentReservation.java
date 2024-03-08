package com.bookingtable.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "paymentReservation")
public class PaymentReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private Double amount;
    @Column
    @CreationTimestamp
    private LocalDate localDate;
    @Column
    private String method;
    @Column
    private String status;
    @OneToOne
    @JoinColumn(name = "reservation")
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
