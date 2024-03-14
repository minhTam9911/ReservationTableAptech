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
@Table(name = "dinnerTableType")
public class DinnerTableType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private int capacity;
    @Column
    private String type;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private Double price;
    @OneToMany(mappedBy = "dinnerTableType", cascade = CascadeType.ALL)
	private Set<DinnerTable> dinnerTables = new HashSet<>();

}
