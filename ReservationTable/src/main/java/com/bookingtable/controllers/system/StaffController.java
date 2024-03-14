package com.bookingtable.controllers.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookingtable.dtos.ResultResponse;

import java.security.Principal;

@Controller
@RequestMapping("staff")
public class StaffController {
	private ResultResponse<String> result = new ResultResponse<>(false,0,"");
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        model.addAttribute("username",principal.getName());
        model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
        return "staff/index";
    }
}
