package com.bookingtable.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.bookingtable.models.Reservation;

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
	
	private double point;
	@NotEmpty
	private String comment;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate created;
	
	private Reservation reservation;
}
