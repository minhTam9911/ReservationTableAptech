package com.bookingtable.controllers.customer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.ReservationRepository;

@RestController
public class DinnerTableAPi {

	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired
	private ReservationRepository repository;
	
	@GetMapping("/api/dinnerTable/{id}/{time}/{date}")
	public ResponseEntity<Integer> index(@PathVariable("id") Integer id,@PathVariable("time") int timeInt, @PathVariable("date") LocalDate date){
		var time = LocalTime.of(timeInt, 0, 0);
		var quantity = dinnerTableRepository.findById(id).get().getQuantity();
		var currentQuantity = quantity;
		var reservation = repository.findAll();
		for(var i : reservation) {
			if(i.getReservationStatus().getId()==1 || i.getReservationStatus().getId()==2) {
				if(i.getBookingDate().equals(date)) {
					if(i.getBookingTime().equals(time)) {
						currentQuantity--;
					}
				}
			}
		}
		return new ResponseEntity<Integer>(currentQuantity, HttpStatus.OK);
		
	}
}
