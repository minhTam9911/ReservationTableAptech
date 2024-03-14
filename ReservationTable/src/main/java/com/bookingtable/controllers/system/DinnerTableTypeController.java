package com.bookingtable.controllers.system;

import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.implement.RestaurantService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("staff/dinnerTableType")
public class DinnerTableTypeController {
	@Autowired
	private RestaurantService iRestaurantService;
	@Autowired
	private IDinnerTableTypeService idinnerTableTypeService;
	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");

	@GetMapping({ "index", "", "/" })
	public String getAllDinnerTableTypes(Model model) {
		List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();

		model.addAttribute("dinnerTableTypes", dinnerTableTypes);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "staff/dinnerTableType/index";
	}

	@GetMapping("create")
	public String create(Model model) {
		DinnerTableTypeDto dinnerTableTypeDto = new DinnerTableTypeDto();
		model.addAttribute("dinnerTableTypeDto", dinnerTableTypeDto);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "staff/dinnerTableType/create";
	}

	@PostMapping("create-submit")
	public String createDinnerTableType(
			@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto,
			 BindingResult bindingResult,Model model, @PathParam("file") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			response.setOption(2);
			response.setMessage("Form input invalid");
			model.addAttribute("msg", response);
			return "staff/dinnerTableType/create";
		}
		var image = FileHelper.uploadCategoryDinnerTable(file);
		dinnerTableTypeDto.setImage(image);
		response = idinnerTableTypeService.createDinnerTableType(dinnerTableTypeDto);
		return "redirect:/staff/dinnerTableType/index";
		

	}

	@GetMapping("edit/{id}")
	public String updateShowingForm(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("dinnerTableTypeDto", idinnerTableTypeService.getDinnerTableTypeById(id));
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "staff/dinnerTableType/edit";
	}

	@PostMapping("edit/submit")
	public String updateDinnerTableType(
			@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto,
			BindingResult bindingResult,Model model,@PathParam("file") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			response.setOption(2);
			response.setMessage("Form input invalid");
			model.addAttribute("msg", response);
			return "staff/dinnerTableType/edit";
		}if (file != null) {
			var categoryImage = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeDto.getId());
			FileHelper.deleteCategoryDinnerTable(categoryImage.getImage());
			var image = FileHelper.uploadCategoryDinnerTable(file);
			dinnerTableTypeDto.setImage(image);
		} else {
			var categoryImage = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeDto.getId());
			dinnerTableTypeDto.setImage(categoryImage.getImage());
		}
		response = idinnerTableTypeService.updateDinnerTableType(dinnerTableTypeDto.getId(), dinnerTableTypeDto);
	
				return "redirect:/staff/dinnerTableType/index";

	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		response = idinnerTableTypeService.deleteDinnerTableType(id);

		attributes.addFlashAttribute("msg", response);
		return "redirect:/staff/dinnerTableType/index";

	}
}
