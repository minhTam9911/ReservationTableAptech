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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.servicies.ICategoryRestaurantService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("staff/categoryRestaurant")
public class CategoryRestaurantController {
	@Autowired
	private ICategoryRestaurantService categoryRestaurantService;
	private ResultResponse<String> result = new ResultResponse<String>(false,0,"");
	
	@GetMapping({"index"})
	public String index(Model model) {
		model.addAttribute("data", categoryRestaurantService.getList());
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false, 0, "");
		return "staff/categoryRestaurant/index";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		CategoryRestaurantDto categoryRestaurantDto = new CategoryRestaurantDto();
		model.addAttribute("categoryRestaurantDto", categoryRestaurantDto);
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false, 0, "");
		return "staff/categoryRestaurant/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("categoryRestaurantDto") CategoryRestaurantDto categoryRestaurantDto,BindingResult bindingResult,
			@PathParam("file") MultipartFile file,
			Model model
			) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", result);
			result = new ResultResponse<>(false, 0, "");
			return "staff/categoryRestaurant/create";
		}
		var image = FileHelper.uploadCategoryRestaurant(file);
		categoryRestaurantDto.setImage(image);
		result = categoryRestaurantService.insert(categoryRestaurantDto);
		if(result.getOption()==1) {
			return "redirect:/staff/categoryRestaurant/index";
		}if(result.getOption() == 2) {
			return "staff/categoryRestaurant/create";
		}
	return "staff/categoryRestaurant/create";
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("categoryRestaurantDto", categoryRestaurantService.getById(id));
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false, 0, "");
		return "staff/categoryRestaurant/edit";
	}
	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("categoryRestaurantDto") CategoryRestaurantDto categoryRestaurantDto,BindingResult bindingResult,
			@PathParam("file") MultipartFile file,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", result);
			result = new ResultResponse<>(false, 0, "");
			return "staff/categoryRestaurant/edit";
		}
		if(!file.isEmpty()) {
			var categoryImage =  categoryRestaurantService.getById(categoryRestaurantDto.getId());
			FileHelper.deleteCategoryRestaurant(categoryImage.getImage());
			var image = FileHelper.uploadCategoryRestaurant(file);
			categoryRestaurantDto.setImage(image);
		}else {
			var categoryImage =  categoryRestaurantService.getById(categoryRestaurantDto.getId());
			categoryRestaurantDto.setImage(categoryImage.getImage());
		}
		result = categoryRestaurantService.update(categoryRestaurantDto.getId(),categoryRestaurantDto);
		if(result.getOption()==1) {
			return "redirect:/staff/categoryRestaurant/index";
		}if(result.getOption()==2) {
		}return "staff/categoryRestaurant/edit";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		result = categoryRestaurantService.delete(id);
			attributes.addFlashAttribute("msg", result);
			return "redirect:/staff/categoryRestaurant/index";
		
		
		
	}
}
