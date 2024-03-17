package com.bookingtable.controllers;

import java.util.Random;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {
    	Random random = new Random();
    	int randomNumber = random.nextInt(2) + 1;
    	if(randomNumber==1) {
    		return "account/error";
    	}else {
    		return "account/cancel";
    	}
        
    }
	
}
