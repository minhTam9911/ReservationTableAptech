package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookingtable.servicies.ISystemService;

@Controller
@RequestMapping("system" )
public class SystemController {
	@Autowired
	private ISystemService systemService;
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {
    	
    	model.addAttribute("data", systemService.getAllSystems());
        return "system/index";
    }

}
