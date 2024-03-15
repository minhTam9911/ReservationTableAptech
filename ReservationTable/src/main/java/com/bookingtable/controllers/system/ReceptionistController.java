package com.bookingtable.controllers.system;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.servicies.IReceptionistService;
import com.bookingtable.servicies.IReservationAgentService;
import com.bookingtable.servicies.IReservationService;
import com.bookingtable.servicies.IRestaurantService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("partner/receptionist")
public class ReceptionistController {
	@Autowired
	private IReceptionistService receptionistService;

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRestaurantService restaurantService;

	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");

	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(Model model, Principal principal, RedirectAttributes redirectAttributess) {
		model.addAttribute("data", receptionistService.getAllReceptionist(principal.getName()));
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/receptionist/index";
	}

	@GetMapping("create")
	public String create(Model model, RedirectAttributes attributes, Principal principal) {
		var receptionistDto = new ReceptionistDto();

		List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsForAgent(principal.getName());
		model.addAttribute("restaurants", restaurants);
		model.addAttribute("receptionistDto", receptionistDto);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/receptionist/create";
	}

	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("receptionistDto") ReceptionistDto receptionistDto,
			BindingResult bindingResult, Principal principal,Model model) {

		
		if (bindingResult.hasErrors()) {
			List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsForAgent(principal.getName());
			model.addAttribute("restaurants", restaurants);
			response.setOption(2);
			response.setMessage("Form input invalid");
			model.addAttribute("msg", response);
			return "partner/receptionist/create";
		}
		var roleData = roleService.getRoleById(4);
		receptionistDto.setRoleDto(roleData);
		var restaurant = restaurantService.getRestaurantById(receptionistDto.getRestaurantDtoId());
		receptionistDto.setRestaurantDto(restaurant);
		response = receptionistService.createReceptionist(receptionistDto, principal.getName());
		if (response.getOption() == 1) {
			return "redirect:/partner/receptionist/index";
		}
		if (response.getOption() == 2) {
		}
		return "partner/receptionist/create";

	}

	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") String id, Principal principal) {
		var receptionistDto = receptionistService.getReceptionistById(id, principal.getName());
		List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsForAgent(principal.getName());
		model.addAttribute("restaurants", restaurants);
		receptionistDto.setRestaurantDtoId(receptionistDto.getRestaurantDto().getId());
		model.addAttribute("receptionistDto", receptionistDto);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/receptionist/edit";
	}

	@PostMapping("update/save")
	public String updateProcess(@Valid @ModelAttribute("receptionistDto") ReceptionistDto receptionistDto, Model model,
			BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsForAgent(principal.getName());
			model.addAttribute("restaurants", restaurants);
			response.setOption(2);
			response.setMessage("Form input invalid");
			model.addAttribute("msg", response);
			response = new ResultResponse<>(false, 0, "");
			return "partner/receptionist/edit";
		}
		var roleData = roleService.getRoleById(4);
		receptionistDto.setRoleDto(roleData);
		var restaurant = restaurantService.getRestaurantById(receptionistDto.getRestaurantDtoId());
		receptionistDto.setRestaurantDto(restaurant);
		response = receptionistService.updateReceptionist(receptionistDto.getId(), receptionistDto,
				principal.getName());
		if (response.getOption() == 1) {

			return "redirect:/partner/receptionist/index";
		}
		if (response.getOption() == 2) {

		}
		return "partner/receptionist/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes attributes, Principal principal) {
		response = receptionistService.deleteReceptionist(id, principal.getName());
		attributes.addFlashAttribute("msg", response);
		return "redirect:/partner/receptionist/index";

	}

	@GetMapping("change/status/{id}")
	public String chageStatus(RedirectAttributes attributes, @PathVariable("id") String id, Principal principal) {
		var check = receptionistService.changeStatus(id, principal.getName());
		if(check) {
			response.setOption(1);
			response.setMessage("Process Successfully");
		}else {
			response.setOption(2);
			response.setMessage("Process Failure");
		}
		return "redirect:/partner/receptionist/index";
	}

}
