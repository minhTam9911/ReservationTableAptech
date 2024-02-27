package com.bookingtable.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GenerateCode {
	public static String Generate()
	{
		 	LocalDateTime now = LocalDateTime.now();
	        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMM"));

	        String randomCode = UUID.randomUUID().toString().substring(0, 10);

	        String output = formattedDateTime + "-" + randomCode;
	        return output;
	}
}
