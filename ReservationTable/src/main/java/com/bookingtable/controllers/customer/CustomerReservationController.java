package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.PaymentDto;
import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.ReservationStatusRepository;
import com.paypal.api.payments.Payment;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("customer/reservation")
public class CustomerReservationController {

	@Autowired 
	private DinnerTableRepository dinnerTableRepository;
	@Autowired 
	private ReservationStatusRepository  reservationStatusRepository;
	@Autowired 
	private CustomerRepository customerRepository;
	public ResultResponse<String> validation = new ResultResponse<String>(false,0,"");
	
	@PostMapping("submit")
	public String submit(@PathParam("idDinnerTable") Integer idDinnerTable,
			@PathParam("date") LocalDate date,
			@PathParam("time") LocalTime time,
			@PathParam("method") Integer method,
			@PathParam("partySize") int partySize,
			Principal principal,Model model, HttpSession session) {
		if(principal!=null) {
			var reservation = new Reservation();
			var dinerTable = dinnerTableRepository.findById(idDinnerTable).get();
			var customer = customerRepository.findByEmail(principal.getName());
			
			if(dinerTable == null) {
				return "dinnerTable-details/cancel";
			}
//			if(partySize > dinerTable.getDinnerTableType().getCapacity()) {
//				validation.setOption(2);
//				validation.setMessage("The total number of people has exceeded the given limit");
//				model.addAttribute("msg", validation);
//				return "redirect:/customer/dinnerTable-details/"+dinerTable.getId();
//			}
			reservation.setReservationStatus(reservationStatusRepository.findById(1).get());
			reservation.setRestaurant(dinerTable.getRestaurant());
			reservation.setBookingDate(date);
			reservation.setBookingTime(time);
			reservation.setCustomer(customer);
			reservation.setDinnerTable(dinerTable);
			reservation.setQuantity(1);
			reservation.setNumberOfPeople(partySize);
			var amount = dinerTable.getDinnerTableType().getPrice();
			session.setAttribute("reservation", reservation);
			if(method==1) {
				var paymentDto = new PaymentDto();
				paymentDto.setFullName(customer.getFullName());
				paymentDto.setEmail(customer.getEmail());
				paymentDto.setAddress(customer.getAddress());
				paymentDto.setAmount(Double.valueOf(amount / 24000 ));
				paymentDto.setPartySize(partySize);
				paymentDto.setCurrency("USD");
				paymentDto.setRestaurant(dinerTable.getRestaurant().getName());
				paymentDto.setDinnerTable("Code "+dinerTable.getId());
				paymentDto.setDescription("I appreciate it! I want to thank you for choosing our restaurant and giving us your trust. I am delighted to hear that we have met your needs and have been able to assist you in achieving your desired results.");
				model.addAttribute("paymentDto", paymentDto);
				return "payment/paypal/index";
			}{
				var paymentDto = new PaymentDto();
				paymentDto.setFullName(customer.getFullName());
				paymentDto.setEmail(customer.getEmail());
				paymentDto.setAddress(customer.getAddress());
				paymentDto.setAmount(amount);
				paymentDto.setPartySize(partySize);
				paymentDto.setCurrency("VND");
				paymentDto.setRestaurant(dinerTable.getRestaurant().getName());
				paymentDto.setDinnerTable("Code "+dinerTable.getId());
				paymentDto.setDescription("I appreciate it! I want to thank you for choosing our restaurant and giving us your trust. I am delighted to hear that we have met your needs and have been able to assist you in achieving your desired results.");
				model.addAttribute("paymentDto", paymentDto);
				return "payment/vnpay/index";
			}
		}
		return "dinnerTable-details/cancel";
	}
	
}
