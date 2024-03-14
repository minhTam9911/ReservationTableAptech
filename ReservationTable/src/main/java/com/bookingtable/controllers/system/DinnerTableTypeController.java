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

		return "staff/dinnerTableType/index";
	}

	@GetMapping("create")
	public String create(Model model) {
		DinnerTableTypeDto dinnerTableTypeDto = new DinnerTableTypeDto();
		model.addAttribute("dinnerTableTypeDto", dinnerTableTypeDto);
		return "staff/dinnerTableType/create";
	}

	@PostMapping("/create")
	public String createDinnerTableType(
			@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto,
			@PathParam("file") MultipartFile file, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "staff/dinnerTableType/create";
		}
		var image = FileHelper.uploadCategoryDinnerTable(file);
		dinnerTableTypeDto.setImage(image);
		response = idinnerTableTypeService.createDinnerTableType(dinnerTableTypeDto);
		if (response.getOption() == 1) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/staff/dinnerTableType/index";
		}
		if (response.getOption() == 2) {

			redirectAttributes.addFlashAttribute("msg", response);
		}
		return "staff/dinnerTableType/create";

	}

	@GetMapping("edit/{id}")
	public String updateShowingForm(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("dinnerTableTypeDto", idinnerTableTypeService.getDinnerTableTypeById(id));
		return "staff/dinnerTableType/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateDinnerTableType(
			@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto,
			@PathParam("file") MultipartFile file, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "staff/dinnerTableType/edit";
		}
		if (file != null) {
			var categoryImage = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeDto.getId());
			FileHelper.deleteCategoryDinnerTable(categoryImage.getImage());
			var image = FileHelper.uploadCategoryDinnerTable(file);
			dinnerTableTypeDto.setImage(image);
		} else {
			var categoryImage = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeDto.getId());
			dinnerTableTypeDto.setImage(categoryImage.getImage());
		}
		System.out.println(dinnerTableTypeDto);
		response = idinnerTableTypeService.updateDinnerTableType(dinnerTableTypeDto.getId(), dinnerTableTypeDto);
		if (response.getOption() == 1) {

			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/staff/dinnerTableType/index";
		}
		if (response.getOption() == 2) {

			redirectAttributes.addFlashAttribute("msg", response);
		}
		return "staff/dinnerTableType/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		response = idinnerTableTypeService.deleteDinnerTableType(id);

		attributes.addFlashAttribute("msg", response);
		return "redirect:/staff/dinnerTableType/index";

	}
}
