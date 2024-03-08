package com.bookingtable.controllers.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("staff")
public class StaffController {
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        model.addAttribute("username",principal.getName());
        return "staff/index";
    }
}
