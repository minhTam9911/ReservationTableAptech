package com.bookingtable.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GenerateCode {
	public static String GenerateReservation()
	{
		 	LocalDateTime now = LocalDateTime.now();
	        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMM"));

	        String randomCode = UUID.randomUUID().toString().substring(0, 10);

	        String output = formattedDateTime + "-" + randomCode;
	        return output;
	}
	
	public static String GenerateRestaurent()
	{
		 	LocalDateTime now = LocalDateTime.now();
	        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMM"));

	        String randomCode = UUID.randomUUID().toString().substring(0, 5);

	        String output = formattedDateTime + "-" + randomCode;
	        return output;
	}
}
