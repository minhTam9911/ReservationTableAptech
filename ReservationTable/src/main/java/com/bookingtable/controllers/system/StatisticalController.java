package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.InvoiceRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IRestaurantStatisticalService;

@Controller
@RequestMapping("partner/statistical")
public class StatisticalController {

	@Autowired
	private IRestaurantStatisticalService restaurantStatisticalService;
	private ResultResponse<String> result = new ResultResponse<>(false,0,"");
	@GetMapping("index")
	public String index(Model model) {
		  model.addAttribute("msg", result);
			result = new ResultResponse<>(false,0,"");
		return "partner/statistical/index";
	}
	
}
