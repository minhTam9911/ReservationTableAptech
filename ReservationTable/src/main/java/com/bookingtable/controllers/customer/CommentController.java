package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.RateDto;
import com.bookingtable.models.Rate;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IRateService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("customer/comment")
public class CommentController {

	@Autowired
	private IRateService rateService;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping("create")
	public String insert(@PathParam("selection") int selection,
			Principal principal,@PathParam("comment") String comment,
			@PathParam("id") Integer id,
			@PathParam("idReservation") String idReservation) {
		if(principal.getName()!=null || !principal.getName().isEmpty()) {
			var rate = new Rate();
			rate.setId(id);
			rate.setCustomer(customerRepository.findByEmail(principal.getName()));
			rate.setReservation(reservationRepository.findById(idReservation).get());
			rate.setComment(comment);
			rate.setPoint(selection);
			rate.setCreated(LocalDate.now());
			rate.setStatus(false);
			rateRepository.save(rate);
			return "redirect:/customer/dinnerTable-details/"+rate.getReservation().getDinnerTable().getId();
			}
		return "customer/accessDined";
	}
}
