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
	@NotNull(message = "Point cannot be null")
	private double point;
	@NotNull(message = "Comment cannot be null")
	private String comment;
	@NotNull(message = "Created cannot be null")
	private LocalDate created;
	
	private DinnerTableDto dinnerTable;
}
