package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
public class ProfileController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IImageService iImageService;
    private ResultResponse<String> result = new ResultResponse<>(false,"");

    @GetMapping("customer/profile")
    public String profile(Model model, Principal principal) {
        var customerDto = customerService.getCustomerByEmail(principal.getName());
        model.addAttribute("customerDto", customerDto);
        return "customer/profile/index";
    }
    @PostMapping("customer/profile/save")
    public String updateProfileProcess(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer/profile/index";
        }

        // Lấy thông tin khách hàng từ dịch vụ
        CustomerDto existingCustomer = customerService.getCustomerByEmail(customerDto.getEmail());

        // Nếu mật khẩu mới không được nhập, giữ nguyên mật khẩu cũ
        if (customerDto.getPassword() == null || customerDto.getPassword().isEmpty()) {
            customerDto.setPassword(existingCustomer.getPassword());
        }

        // Thực hiện cập nhật thông tin khách hàng
        var response = customerService.updateCustomer(customerDto.getId(), customerDto);
        if(response.isStatus()) {
            this.result.setStatus(true);
            return "redirect:/customer/profile/index";
        } else {
            // Xử lý khi cập nhật không thành công
            return "customer/profile/index";
        }
    }

}
