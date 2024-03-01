package com.bookingtable.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DinnerTableTypeDto {
    private Integer id;
    @NotNull
    @NotEmpty
    private int capacity;
    @NotNull
    @NotEmpty
    private String type;
    private String description;
    @OneToMany(mappedBy = "dinnerTableType", cascade = CascadeType.ALL)
	private Set<DinnerTableDto> dinnerTablesDto = new HashSet<>();

}
