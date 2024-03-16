package com.bookingtable.servicies;

import com.bookingtable.dtos.SystemDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.bookingtable.dtos.ResultResponse;

import java.util.Objects;

public interface IAccountService<T> extends UserDetailsService  {
	ResultResponse<String> forgotPassword(String email);
	ResultResponse<String> verifyCode(String email, String code);
	ResultResponse<String> saveResetPassword(String email,String password);
	public ResultResponse<String> updateProfile(String fullname, String phoneNumer, String address, MultipartFile file,String email);
	ResultResponse<String> changePassword(String email);
	public T findByEmail(String email);

}
