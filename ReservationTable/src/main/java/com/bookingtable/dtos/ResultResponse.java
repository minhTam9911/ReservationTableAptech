package com.bookingtable.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {

	private boolean status;
	private String check;
	private T message;
	public ResultResponse(T message) {
		super();
		this.message = message;
	}
	public ResultResponse(boolean status, T message) {
		super();
		this.status = status;
		this.message = message;
	}
	
}
