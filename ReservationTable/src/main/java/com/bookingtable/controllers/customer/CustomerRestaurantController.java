package com.bookingtable.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.IRestaurantService;

@Controller
@RequestMapping("restaurant")
public class CustomerRestaurantController {

	@Autowired
	private IRestaurantService restaurantService;
	
	
	@GetMapping("index")
	public String index(Model model) {
		var data = restaurantService.getAllRestaurants();
		model.addAttribute("data", data);
		return "customer/restaurant/index";
	}
}
