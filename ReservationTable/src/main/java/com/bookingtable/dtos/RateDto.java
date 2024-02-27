package com.bookingtable.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RateDto {

	
	private Integer id;
	@NotNull
    @NotEmpty
	private double point;
	@NotNull
    @NotEmpty
	private String comment;
	
	private LocalDate created;
	 
	private RateTypeDto rateType;
	
	private DinnerTableDto dinnerTable;
}
