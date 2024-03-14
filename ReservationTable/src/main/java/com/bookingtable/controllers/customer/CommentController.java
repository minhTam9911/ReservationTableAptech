package com.bookingtable.controllers.customer;

import java.security.Principal;
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
	
	@PostMapping("insert/{idDinnerTable}")
	public String insert(@Valid @ModelAttribute("comment") Rate comment,
			Principal principal,
			@PathVariable("idReservation") String idReservation) {
		if(principal.getName()!=null || !principal.getName().isEmpty()) {
			comment.setCustomer(customerRepository.findByEmail(principal.getName()));
			comment.setReservation(reservationRepository.findById(idReservation).get());
			rateRepository.save(comment);
			return "redirect:/customer/dinnerTable/"+comment.getReservation().getDinnerTable().getId();
			}
		return "customer/accessDined";
	}
}
