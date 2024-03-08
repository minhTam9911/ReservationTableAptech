package com.bookingtable.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dinnerTable")
public class DinnerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private int quantity;
    @Column
    private String status;
    @Column
    private String currentQuantity;
    @ManyToOne
    @JoinColumn(name = "dinnerTableType_id")
    private DinnerTableType dinnerTableType;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "dinnerTable", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

}
