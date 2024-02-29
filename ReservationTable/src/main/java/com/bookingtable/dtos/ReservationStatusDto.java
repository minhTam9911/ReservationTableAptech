package com.bookingtable.dtos;

import java.util.ArrayList;
import java.util.Collection;

import com.bookingtable.models.Reservation;


import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationStatusDto {

	private Integer id;

	@NotNull
	@NotEmpty
	private String status;
	@NotNull
	@NotEmpty
	private String reason;

	private String description;

	private Collection<ReservationDto> reservationsDto = new ArrayList<>();
}
