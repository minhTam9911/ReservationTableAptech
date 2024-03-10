package com.bookingtable.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.IRestaurantService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("customer/restaurant")
public class CustomerRestaurantController {

	@Autowired
	private IRestaurantService restaurantService;
	
	
	@GetMapping({"index","/",""})
	public String index(Model model) {
		String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
		model.addAttribute("requestURI", requestURI);
		var data = restaurantService.getAllRestaurants();
		model.addAttribute("data", data);
		return "customer/restaurant/index";
	}
}
