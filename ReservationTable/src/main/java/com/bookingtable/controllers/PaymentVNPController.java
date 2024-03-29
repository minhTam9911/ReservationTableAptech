package com.bookingtable.controllers;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.bookingtable.configurations.VNPConfig;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.models.Invoice;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IInvoiceService;
import com.bookingtable.servicies.IMailService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("payment/vnpay")
public class PaymentVNPController {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private IInvoiceService invoiceService;
	@Autowired
	private IMailService iMailService;
	@Autowired
	private Environment environment;

	@PostMapping("create-payment")
	public RedirectView createPaymentVNP(@PathParam("amount") String amount, HttpSession session)
			throws UnsupportedEncodingException, UnknownHostException {

		String orderType = "other";
		var amoutParse = amount.replace(".0", "");
		;
		long amountInput = Long.valueOf(amoutParse) * 100;
		String bankCodeInput = "NCB"; // bankcode

		String vnp_TxnRef = VNPConfig.getRandomNumber(8);
		InetAddress localHost = InetAddress.getLocalHost();
		String vnp_IpAddr = localHost.getHostAddress().toString();

		String vnp_TmnCode = VNPConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", VNPConfig.vnp_Version);
		vnp_Params.put("vnp_Command", VNPConfig.vnp_Command);
		vnp_Params.put("vnp_TmnCode", VNPConfig.vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amountInput));
		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCodeInput != null && !bankCodeInput.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCodeInput);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = "vn"; // language
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", VNPConfig.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPConfig.hmacSHA512(VNPConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNPConfig.vnp_PayUrl + "?" + queryUrl;
		session.setAttribute("status", true);
		session.setAttribute("message", "Process successful");
		session.setAttribute("url", paymentUrl);

//	        com.google.gson.JsonObject job = new JsonObject();
//	        job.addProperty("code", "00");
//	        job.addProperty("message", "success");
//	        job.addProperty("data", paymentUrl);
//	        Gson gson = new Gson();
//	        resp.getWriter().write(gson.toJson(job));
		System.out.println(paymentUrl);
		return new RedirectView(paymentUrl);
	}

	@GetMapping("success")
	public String transactionVNP(@RequestParam(value = "vnp_Amount") String amount,
			@RequestParam("vnp_BankCode") String bankCode, @RequestParam("vnp_OrderInfo") String ortherInfo,
			@RequestParam("vnp_ResponseCode") String responseCode, HttpSession session) {

		if (responseCode.equals("00")) {
			Reservation reservation = (Reservation) session.getAttribute("reservation");
			reservation.setCreated(LocalDateTime.now());
			var reservationSaveChange = reservationRepository.save(reservation);
			var invoice = new Invoice();
			invoice.setReservation(reservation);
			var data = invoiceService.insert(invoice);

			String emailFrom = environment.getProperty("spring.mail.username");
			iMailService.send(emailFrom, data.getReservation().getCustomer().getEmail(),
					"Table reservation payment invoice", MailHelper.HtmlNotification(data));
			session.removeAttribute("reservation");
			return "payment/vnpay/success";
		} else {
			return "payment/vnpay/cancel";
		}

	}

	@GetMapping("index")
    public String handleException() {
		return "payment/vnpay/error";
    }
}
