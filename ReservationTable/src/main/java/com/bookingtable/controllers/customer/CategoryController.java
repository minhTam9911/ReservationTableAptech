package com.bookingtable.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.ICategoryRestaurantService;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IRestaurantService;

@Controller
@RequestMapping("customer/category")
public class CategoryController {

	@Autowired
	private IDinnerTableTypeService dinnerTableTypeService;
	@Autowired
	private ICategoryRestaurantService categoryRestaurantService;
	@Autowired
	private IRestaurantService restaurantService;
	@Autowired
	private IDinnerTableService dinnerTableService;
	
	@GetMapping("restaurant")
	public String indexRestaurant(Model model) {
		model.addAttribute("data", categoryRestaurantService.getList());
		return "customer/category/restaurant";
	}
	
	@GetMapping("dinnerTable")
	public String indexDinnerTable(Model model) {
		model.addAttribute("data", dinnerTableTypeService.getAllDinnerTablesType());
		return "customer/category/dinnerTable";
	}
	@GetMapping("restaurant/detail/{id}")
	public String detailRestaurant(Model model,@PathVariable("id") Integer id) {
		model.addAttribute("data", restaurantService.getAllCategory(id));
		return "customer/category/detail-restaurant";
	}
	
	@GetMapping("dinnerTable/detail/{id}")
	public String detailDinnerTable(Model model,@PathVariable("id") Integer id) {
		model.addAttribute("data", dinnerTableService.getAllCategory(id));
		return "customer/category/detail-dinnerTable";
	}
	
}
