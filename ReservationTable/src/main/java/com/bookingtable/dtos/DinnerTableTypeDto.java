package com.bookingtable.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "Capacity cannot be empty")
    @Positive
    private int capacity;
    @NotNull(message="Type cannot be empty")
    @NotEmpty
    private String type;
    private String description;

    private Set<DinnerTableDto> dinnerTablesDto = new HashSet<>();
}
