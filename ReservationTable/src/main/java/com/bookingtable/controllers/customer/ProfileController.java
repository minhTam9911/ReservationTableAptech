package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IImageService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping
public class ProfileController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IImageService iImageService;
    private ResultResponse<String> result = new ResultResponse<String>(false,0,"");

    @GetMapping("/customer/profile")
    public String profile(Model model, Principal principal) {
        var customerDto = customerService.getCustomerByEmail(principal.getName());
        model.addAttribute("customerDto", customerDto);
        return "customer/profile/index";
    }
    

    @GetMapping("/customer/profile/edit")
    public String edit(Model model, Principal principal) {
        var customerDto = customerService.getCustomerByEmail(principal.getName());
        model.addAttribute("customerDto", customerDto);
        model.addAttribute("msg", result);
        result =  new ResultResponse<String>(false,0,"");
        return "customer/profile/edit";
    }
    @PostMapping("/customer/profile/edit/submit")
    public String updateProfileProcess(Model model, Principal principal,@PathParam("fullname") String fullname,@PathParam("address") String address,@PathParam("phoneNumber") String phoneNumber,@PathParam("dateOfBirth") LocalDate dateOfBirth ) {
        result = customerService.updateCustomer(fullname,address,phoneNumber,dateOfBirth,principal.getName());
        model.addAttribute("msg", result);
        result =  new ResultResponse<String>(false,0,"");
        var customerDto = customerService.getCustomerByEmail(principal.getName());
        model.addAttribute("customerDto", customerDto);
        return "customer/profile/edit";
    }
    
    @PostMapping("/customer/profile/upload")
    public String updateProfileProcess(@PathParam("file") MultipartFile file, Model model, Principal principal ) {
        result = customerService.uploadImage(file, principal.getName());
        model.addAttribute("msg", result);
        result =  new ResultResponse<String>(false,0,"");
        var customerDto = customerService.getCustomerByEmail(principal.getName());
        model.addAttribute("customerDto", customerDto);
        return "customer/profile/edit";
    }

}
