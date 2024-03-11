package com.bookingtable.dtos;

import com.bookingtable.models.Reservation;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
	
	
	private String id;
	
    private Reservation reservation;
}
