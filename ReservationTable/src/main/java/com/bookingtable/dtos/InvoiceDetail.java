package com.bookingtable.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetail {

	private String idInvoice;
	private String fullName;
	private LocalDateTime created;
	private String restaurant;
	private Integer dinnerTable;
	private Integer quantity;
	private Double amount;
	private String city;
	private String district;
	private String ward;
	private String address;
	private String status;
	private String reservationId;
}
