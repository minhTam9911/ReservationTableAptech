package com.bookingtable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.bookingtable.configurations.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("payment/paypal")
@Slf4j
public class PaymentPaypalController {

	@Autowired
	private  PaypalService paypalService;
	
	
	@GetMapping("index")
	public String index() {
		return "payment/paypal/index";
	}
	
	@PostMapping("create-payment")
	public RedirectView createPayment(
			@RequestParam("method") String method,
			@RequestParam("currency") String currency,
			@RequestParam("amount") String amount,
			@RequestParam("description") String description,
			@RequestParam("intent") String intent
			) {
		try {
			String cancelUrl = "http://localhost:8080/payment/paypal/cancel";
			String successUrl = "http://localhost:8080/payment/paypal/success";
			Payment  payment = paypalService.createPayment(
					Double.valueOf(amount),
					currency,
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
			@RequestParam("PayerID") String payerId
			) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")) {
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
