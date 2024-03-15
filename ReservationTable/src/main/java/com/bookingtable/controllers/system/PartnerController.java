package com.bookingtable.controllers.system;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookingtable.dtos.ResultResponse;

import java.security.Principal;

@Controller
@RequestMapping("partner")
public class PartnerController {
	private ResultResponse<String> response = new ResultResponse<>(false,0,"");
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        model.addAttribute("username",authentication.getName());
        model.addAttribute("msg", response);
    	response = new ResultResponse<>(false, 0, "");
    	
        return "partner/index";
    }
}
