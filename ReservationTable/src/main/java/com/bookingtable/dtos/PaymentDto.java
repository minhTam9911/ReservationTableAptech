package com.bookingtable.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

	private String fullName;
	private String email;
	private String address;
	private String restaurant;
	private String dinnerTable;
	private Integer partySize;
	private String currency;
	private String description;
	private Double amount;
	
}
