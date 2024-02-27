package com.bookingtable.controllers.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({ "", "customer/home", "/" })
public class HomeController {

	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index() {
		return "customer/home/index";
	}
	
}
