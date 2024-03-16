package com.bookingtable.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RestaurantStatisticalDto {
	private LocalDate date;

	private Integer totalBookinged;

	private Integer totalFinished;

	private Integer totalCanceled;

	private Integer totalDinnerTable;
	private Integer totalCustomer;

	public RestaurantStatisticalDto(LocalDate date, Integer totalBookinged, Integer totalFinished,
			Integer totalCanceled, Integer totalDinnerTable, Integer totalCustomer) {
		this.totalBookinged = totalBookinged;
		this.totalFinished = totalFinished;
		this.totalCanceled = totalCanceled;
		this.totalCustomer = totalCustomer;
		this.totalDinnerTable = totalDinnerTable;
		this.date = date;
	}

}
