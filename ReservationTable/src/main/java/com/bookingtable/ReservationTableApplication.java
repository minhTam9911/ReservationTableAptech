package com.bookingtable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
public class ReservationTableApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationTableApplication.class, args);
	}

}
