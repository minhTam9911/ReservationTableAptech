package com.bookingtable.servicies;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookingtable.dtos.ResultResponse;

public interface IAccountService extends UserDetailsService  {
	ResultResponse<String> forgotPassword(String email);
	ResultResponse<String> verifyCode(String email, String code);
	ResultResponse<String> saveResetPassword(String email,String password);
}
