package com.bookingtable.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.bookingtable.configurations.PaypalService;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.models.Invoice;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IInvoiceService;
import com.bookingtable.servicies.IMailService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("payment/paypal")
@Slf4j
public class PaymentPaypalController {

	@Autowired
	private  PaypalService paypalService;
	@Autowired 
	private ReservationRepository reservationRepository;
	@Autowired
	private IInvoiceService invoiceService;
	@Autowired 
	private IMailService iMailService;
	@Autowired
	private Environment environment;
	
	@GetMapping("index")
	public String index() {
		return "payment/paypal/index";
	}
	
	@PostMapping("create-payment")
	public RedirectView createPayment(
			@RequestParam("amount") String amount,
			@RequestParam("description") String description) {
		try {
			String cancelUrl = "http://localhost:8080/payment/paypal/cancel";
			String successUrl = "http://localhost:8080/payment/paypal/success";
//			String cancelUrl = "http://172.16.7.43:8091/payment/paypal/cancel";
//			String successUrl = "http://172.16.7.43:8091/payment/paypal/success";
			Payment  payment = paypalService.createPayment(
					Double.valueOf(amount),
					"USD",
					"paypal",
					"sale",
					description,
					cancelUrl,
					successUrl);
			for(Links links : payment.getLinks()) {
				if(links.getRel().equals("approval_url")) {
					return new RedirectView(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			log.error("Error :: ",e);
		}
		return new RedirectView("payment/paypal/error");
	}
	
	@GetMapping("success")
	public String paymentSuccess(
			@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId,
			HttpSession session) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")) {
				Reservation reservation = (Reservation) session.getAttribute("reservation");
				reservation.setCreated(LocalDateTime.now());
				var reservationSaveChange = reservationRepository.save(reservation);
				var invoice = new Invoice();
				invoice.setReservation(reservation);
				var data =  invoiceService.insert(invoice);
				 String emailFrom = environment.getProperty("spring.mail.username");
		            iMailService.send(emailFrom, data.getReservation().getCustomer().getEmail(), "Table reservation payment invoice", MailHelper.HtmlNotification(data));
				session.removeAttribute("reservation");
				return "payment/paypal/success";
			}
		} catch (PayPalRESTException e) {
			log.error("Error :: ",e);
		}
		return "payment/paypal/success";
	}
	
	@GetMapping("cancel")
	public String paymentCancel() {
		return "payment/paypal/cancel";
	}
	
	@GetMapping("error")
	public String paymentError() {
		return "payment/paypal/error";
	}
}
