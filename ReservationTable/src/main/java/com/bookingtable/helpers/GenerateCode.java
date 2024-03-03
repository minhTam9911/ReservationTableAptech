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
	public static String GeneratePassword(int n) 
	 { 
	 
	  // choose a Character random from this String 
	  String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	         + "0123456789"
	         + "abcdefghijklmnopqrstuvxyz"; 
	 
	  // create StringBuffer size of AlphaNumericString 
	  StringBuilder sb = new StringBuilder(n); 
	 
	  for (int i = 0; i < n; i++) { 
	 
	   // generate a random number between 
	   // 0 to AlphaNumericString variable length 
	   int index 
	    = (int)(AlphaNumericString.length() 
	      * Math.random()); 
	 
	   // add Character one by one in end of sb 
	   sb.append(AlphaNumericString 
	      .charAt(index)); 
	  } 
	 
	  return sb.toString(); 
	 } 
}
