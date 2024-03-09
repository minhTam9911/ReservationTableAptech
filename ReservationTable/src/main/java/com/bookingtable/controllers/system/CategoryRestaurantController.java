package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.ICategoryRestaurantService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("staff/category-restaurant")
public class CategoryRestaurantController {
	@Autowired
	private ICategoryRestaurantService categoryRestaurantService;
	
	@GetMapping({"index"})
	public String index(Model model) {
		model.addAttribute("data", categoryRestaurantService.getList());
		return "staff/categoryRestaurant/index";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		CategoryRestaurantDto categoryRestaurantDto = new CategoryRestaurantDto();
		model.addAttribute("categoryRestaurantDto", categoryRestaurantDto);
		return "staff/categoryRestaurant/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("categoryRestaurantDto") CategoryRestaurantDto categoryRestaurantDto,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "staff/categoryRestaurant/create";
		}
		var response = categoryRestaurantService.insert(categoryRestaurantDto);
		if(response.isStatus()) {
			return "redirect:/staff/categoryRestaurant/index";
		}else {
			  
		       bindingResult.addError(new FieldError("categoryRestaurantDto","name", response.getMessage().getName()));
		       return "staff/categoryRestaurant/create";
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("categoryRestaurantDto", categoryRestaurantService.getById(id));
		return "staff/categoryRestaurant/edit";
	}
	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("categoryRestaurantDto") CategoryRestaurantDto categoryRestaurantDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "staff/categoryRestaurant/edit";
		}
		
		var response = categoryRestaurantService.update(categoryRestaurantDto.getId(),categoryRestaurantDto);
		if(response.isStatus()) {
			
			return "redirect:/staff/categoryRestaurant/index";
		}else {
			 bindingResult.addError(new FieldError("categoryRestaurantDto","name", response.getMessage().getName()));
		       return "staff/categoryRestaurant/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		var response = categoryRestaurantService.delete(id);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/staff/categoryRestaurant/index";
		}else {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/staff/categoryRestaurant/index";
		}
	}
}
