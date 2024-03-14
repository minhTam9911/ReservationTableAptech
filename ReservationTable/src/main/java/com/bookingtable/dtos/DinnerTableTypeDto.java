package com.bookingtable.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Min(value = 1)
    private int capacity;

    @Min(value = 10000)
    private double price;

    @NotEmpty(message="Type cannot be null")
    private String type;

    @NotEmpty(message="Type cannot be null")
    private String description;
    
    private String image;
    private Set<DinnerTableDto> dinnerTablesDto = new HashSet<>();
}
