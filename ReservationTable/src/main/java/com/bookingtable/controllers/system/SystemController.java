package com.bookingtable.controllers.system;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.IRoleService;
import com.bookingtable.servicies.ISystemService;
import com.bookingtable.servicies.implement.RoleService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("admin/panel")
public class SystemController {
	@Autowired
	private ISystemService systemService;

	@Autowired
	private IRoleService roleService;

	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");

	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("data", systemService.getAllSystems());
		return "admin/panel/staff/index";
	}

	@GetMapping("create")
	public String create(Model model, RedirectAttributes attributes) {
		var systemDto = new SystemDto();
		model.addAttribute("systemDto", systemDto);
		return "admin/panel/staff/create";
	}

	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("systemDto") SystemDto systemDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {

		var roleData = roleService.getRoleById(2);
		systemDto.setRoleDto(roleData);
		if (bindingResult.hasErrors()) {
			return "admin/panel/staff/create";
		}
		response = systemService.insertSystem(systemDto);
		if (response.getOption() == 1) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/admin/panel/index";
		}
		if (response.getOption() == 2) {
			redirectAttributes.addFlashAttribute("msg", response);
		}
		return "redirect:/admin/panel/create";

	}

	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") String id) {
		var systemDto = systemService.getSystemsById(id);
		model.addAttribute("systemDto", systemDto);
		return "admin/panel/staff/edit";
	}

	@PostMapping("update/save")
	public String updateProcess(@Valid @ModelAttribute("systemDto") SystemDto systemDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/panel/staff/edit";
		}
		response = systemService.updateSystem(systemDto.getId(), systemDto);
		if (response.getOption() == 1) {
			redirectAttributes.addFlashAttribute("msg", response);
			this.response.setStatus(true);

			return "redirect:/admin/panel/index";
		}
		if (response.getOption() == 2) {
			redirectAttributes.addFlashAttribute("msg", response);

		}
		return "admin/panel/staff/edit";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes attributes) {
		 response = systemService.deleteSystem(id);
		 attributes.addFlashAttribute("msg",response);
			return "redirect:/admin/panel/index";
		
	}

	@GetMapping("change/status/{id}")
	public String chageStatus(RedirectAttributes attributes, @PathVariable("id") String id) {
		var check = systemService.changeStatus(id);
		attributes.addFlashAttribute("msg", check);
		return "redirect:/admin/panel/index";
	}

}
