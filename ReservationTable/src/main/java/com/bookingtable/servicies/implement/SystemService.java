package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IMailService;
import com.bookingtable.servicies.ISystemService;

@Service
public class SystemService implements ISystemService {

	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private CustomerRepository guestRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private IMailService mailService;
	@Autowired
	private Environment environment;
	@Autowired
	private BCryptPasswordEncoder cryptPasswordEncoder;
	@Override
	public List<SystemDto> getAllSystems() {

		return systemRepository.findByRoleIdNot(1).stream().map(i->SystemMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public SystemDto getSystemsById(String id) {
		return SystemMapper.mapToDto(systemRepository.findById(id).get());
	}



	@Override
	public  ResultResponse<SystemDto> updateSystem(String id, SystemDto systemDto) {
		try {

			if(receptionistRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(reservationAgentRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(guestRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(systemRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			var data = systemRepository.findById(id).get();
			data.setFullname(systemDto.getFullname());
			data.setAddress(systemDto.getAddress());
			data.setDateOfBirth(systemDto.getDateOfBirth());
			data.setEmail(systemDto.getEmail());
			data.setPhoneNumber(systemDto.getPhoneNumber());
			data.setGender(systemDto.isGender());
			data.setUpdated(LocalDate.now());
			if(systemRepository.save(data)!=null) {
				return new  ResultResponse<SystemDto>(true, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto(e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<SystemDto> deleteSystem(String id) {
		try {

			if(systemRepository.findById(id)!=null) {
				systemRepository.deleteById(id);
                return new ResultResponse<SystemDto>(true, new SystemDto());
            } else {
                return new ResultResponse<SystemDto>(false, new SystemDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<SystemDto>(false, new SystemDto(e.getMessage()));
        }
	}

	@Override
	public  ResultResponse<SystemDto> insertSystem(SystemDto systemDto) {
		try {
			systemDto.setEmail(systemDto.getEmail().toLowerCase());

			if(systemRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(receptionistRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(guestRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(systemRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			var data = SystemMapper.mapToModel(systemDto);
			var password = GenerateCode.GeneratePassword(12);
			var hashPassword = cryptPasswordEncoder.encode(password);
			data.setPassword(hashPassword);
			String email = environment.getProperty("spring.mail.username");
			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), password);
			if (mailService.send(email, data.getEmail(), "Account for you", content)) {
				
			} else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Send Email Fail"));
			}
			
			if(systemRepository.save(data)!=null) {
				return new  ResultResponse<SystemDto>(true, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
		}
	}

	@Override
	public boolean changeStatus(String id) {
		try {
			var data = systemRepository.findById(id).get();
			if(data== null) {
				return false;
			}
			data.setStatus(!data.isStatus());
			if(systemRepository.save(data)!=null) return true;
			return false;
		}catch (Exception e) {
			return false;
		}
		
	}

}
