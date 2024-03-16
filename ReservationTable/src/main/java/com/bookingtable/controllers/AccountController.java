package com.bookingtable.controllers;

import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.servicies.IAccountService;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IRoleService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ResultResponse<String> result = new ResultResponse<>(false, 0, "");
    @Autowired
    private ISystemService systemService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Principal principal,
                        RedirectAttributes redirectAttributes, Model model) {
        if (principal == null) {
            var customerDto = new CustomerDto();
            model.addAttribute("customerDto", customerDto);
            if (error != null) {
                result.setOption(2);
                result.setStatus(true);
                result.setMessage("Username and password invalid");
                model.addAttribute("msg", result);
                return "account/login";
            }

            model.addAttribute("msg", result);
            result = new ResultResponse<>(false, 0, "");
            return "account/login";
        }result = new ResultResponse<>(false, 0, "");
        return "redirect:/accessDenied";

    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                           BindingResult bindingResult, RedirectAttributes attributes, Model model) {
        var roleData = roleService.getRoleById(5);
        customerDto.setCreated(LocalDate.now());
        customerDto.setRoleDto(roleData);
        if (bindingResult.hasErrors()) {
            result = new ResultResponse<String>(true, 2, "Form invalid");
            model.addAttribute("msg", result);
            return "account/login";
        }
        result = customerService.createCustomer(customerDto);
        return "redirect:/login";
    }

    @GetMapping("verify")
    public String verifyActive(@PathParam("email") String email, @PathParam("securityCode") String securityCode, RedirectAttributes attributes) {
        var status = customerService.changeStatus(email, securityCode);
        if (status) {
            result.setOption(1);
            result.setMessage("Success activation");
            result.setStatus(true);

        } else {
            result.setOption(2);
            result.setMessage("Failure activation");
            result.setStatus(true);
        }
        return "redirect:/login";
    }


    @GetMapping("/forgot-password")
    public String forgot(Model model) {
        model.addAttribute("msg", result);
        result = new ResultResponse<>(false, 0, "");
        return "account/forgotPassword";
    }


    @GetMapping("/reset-password")
    public String resetPassword(HttpSession session, Model model) {
        if (session.getAttribute("email") != null) {
            model.addAttribute("msg", result);
            result = new ResultResponse<>(false, 0, "");
            return "account/resetPassword";
        }
        return "account/accessDenied";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "account/accessDenied";
    }

    @GetMapping("/verify-code")
    public String verifyCode(HttpSession session, Model model) {
        if (session.getAttribute("email") != null) {
            model.addAttribute("msg", result);
            result = new ResultResponse<>(false, 0, "");
            return "account/verificationCode";
        }
        return "account/accessDenied";
    }


    @PostMapping("/forgot-password/submit")
    public String forgot(RedirectAttributes attributes, Model model, HttpSession session, @PathParam("email") String email) {
        result = accountService.forgotPassword(email);
        System.out.println(result);
        if (result.getOption() == 1) {
            model.addAttribute("msg", result);
            session.setAttribute("email", email);
            result = new ResultResponse<>(false, 0, "");
            return "account/verificationCode";
        }
        if (result.getOption() == 2) {
            return "redirect:/forgot-password";
        }
        return "redirect:/forgot-password";

    }

    @PostMapping("/verify/submit")
    public String verifyCode(RedirectAttributes attributes, Model model, HttpSession session, @PathParam("code") String[] code) {
        if (session.getAttribute("email") != null) {
            String verifyCode = "";
            for (var i : code) {
                verifyCode += i;
            }
            result = accountService.verifyCode(session.getAttribute("email").toString(), verifyCode);
            if (result.getOption() == 1) {
                return "redirect:/reset-password";
            }
            return "redirect:/verify-code";
        }
        return "account/accessDenied";

    }

    @PostMapping("/reset-password/save")
    public String resetPassword(RedirectAttributes attributes, Model model, HttpSession session, @PathParam("newPassword") String newPassword, @PathParam("confirmPassword") String confirmPassword) {
        if (session.getAttribute("email") != null) {
            if (!newPassword.equals(confirmPassword)) {
                result.setOption(2);
                result.setMessage("Confirm password does not match new password");
                return "redirect:/reset-password";
            }
            if(newPassword.length()<8) {
            	result.setOption(2);
                result.setMessage("Password length must be more than 8 characters");
                return "redirect:/reset-password";
            }
            var hashPassword = bCryptPasswordEncoder.encode(newPassword);
            result = accountService.saveResetPassword(session.getAttribute("email").toString(), hashPassword);
            if (result.getOption() == 1) {
                session.removeAttribute("email");
                return "redirect:/login";
            }
            return "redirect:/reset-password";
        }
        return "account/accessDenied";
    }

    @GetMapping("/profile")
    public String profileForm(Model model, Principal principal) {
        var systemDto = accountService.findByEmail(principal.getName());
        
        model.addAttribute("data", systemDto);
        model.addAttribute("msg", result);
        result = new ResultResponse<>(false, 0, "");
        return "/account/profile";
    }
    
    @GetMapping("/profile/edit")
    public String profileEdit(Model model, Principal principal) {
        var systemDto = accountService.findByEmail(principal.getName());
        model.addAttribute("data", systemDto);
        model.addAttribute("msg", result);
        result = new ResultResponse<>(false, 0, "");
        return "/account/profile-edit";
    }

    @PostMapping("/profile/edit/submit")
    public String profileEditSubmit(Model model, Principal principal, @PathParam("fullname") String fullname,  @PathParam("phoneNumber") String phoneNumber, @PathParam("address") String address, @PathParam("file") MultipartFile file) {
        result = accountService.updateProfile(fullname, phoneNumber,address,file,principal.getName());
        if (result.getOption() == 1) {
            return "redirect:/profile";
        } 
        result = new ResultResponse<>(false, 0, "");
        return "/account/profile-edit";
    }

    
    @GetMapping("/change-password")
    public String changePassword(Model model) {
      //  var systemDto = accountService.findByEmail(principal.getName());
   //     model.addAttribute("data", systemDto);
        model.addAttribute("msg", result);
        result = new ResultResponse<>(false, 0, "");
        return "/account/changePassword";
    }
    @PostMapping("/change-password/save")
    public String changePassword(Model model, Principal principal,@PathParam("oldPassword") String oldPassword,@PathParam("newPassword") String newPassword, @PathParam("confirmPassword") String confirmPassword) {
            if (!newPassword.equals(confirmPassword)) {
                result.setOption(2);
                result.setMessage("Confirm password does not match new password");
                model.addAttribute("msg", result);
                result = new ResultResponse<>(false, 0, "");
                return "/account/changePassword";
            }
            if(newPassword.length()<8) {
            	result.setOption(2);
                result.setMessage("Password length must be more than 8 characters");
                model.addAttribute("msg", result);
                result = new ResultResponse<>(false, 0, "");
                return "/account/changePassword";
            }
            var changePass = accountService.changePassword(principal.getName());
            if(changePass .getOption()== 1) {
            	if(bCryptPasswordEncoder.matches(oldPassword, changePass.getMessage().toString())) {
            		var hashPassword = bCryptPasswordEncoder.encode(newPassword);
                    result = accountService.saveResetPassword(principal.getName(), hashPassword);
                    if (result.getOption() == 1) {
                    	result = new ResultResponse<>(false, 0, "");
                        return "redirect:/";
                    }
                    
            	}else {
            		 result.setOption(2);
                     result.setMessage("Old password does not match");
                     model.addAttribute("msg", result);
                     result = new ResultResponse<>(false, 0, "");
                     return "/account/changePassword";
            	}
            }
            result.setOption(2);
            result.setMessage("User not found");
            model.addAttribute("msg", result);
            result = new ResultResponse<>(false, 0, "");
            return "/account/changePassword";
    }
}
