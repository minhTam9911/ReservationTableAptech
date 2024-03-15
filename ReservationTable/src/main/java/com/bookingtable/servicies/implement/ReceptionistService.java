package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.mappers.ReceptionistMapper;
import com.bookingtable.mappers.RestaurantMapper;
import com.bookingtable.models.Restaurant;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IMailService;
import com.bookingtable.servicies.IReceptionistService;
@Service
public class ReceptionistService implements IReceptionistService {

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
	public List<ReceptionistDto> getAllReceptionist(String emailCreatedBy) {

		return receptionistRepository.findByReservationAgentEmail(emailCreatedBy).stream().map(i->ReceptionistMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ReceptionistDto getReceptionistById(String id,String emailCreatedBy) {
		var data = receptionistRepository.findById(id).get();
		if(data!=null && data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
			return ReceptionistMapper.mapToDto(data);
		}
		return null;
	}



	@Override
	public  ResultResponse<String> updateReceptionist(String id, ReceptionistDto receptionistDto, String emailCreatedBy) {
		try {

			if(receptionistRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(reservationAgentRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(guestRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(systemRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			var data = receptionistRepository.findById(id).get();
			if(data==null || !data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				return new  ResultResponse<String>(true,2, "Forbiden");
			}
			data.setFullname(receptionistDto.getFullname());
			data.setAddress(receptionistDto.getAddress());
			data.setEmail(receptionistDto.getEmail());
			data.setPhoneNumber(receptionistDto.getPhoneNumber());
			data.setGender(receptionistDto.isGender());
			data.setRestaurant(RestaurantMapper.mapToModel(receptionistDto.getRestaurantDto()));
			data.setUpdated(LocalDate.now());
			if(receptionistRepository.save(data)!=null) {
				return new  ResultResponse<String>(true,1, "Process Successfully");
			}else {
				return new  ResultResponse<String>(true,2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<String>(true,2, e.getMessage());
		}
	}

	@Override
	public  ResultResponse<String> deleteReceptionist(String id, String emailCreatedBy) {
		try {
			var data = receptionistRepository.findById(id).get();
			if(data!=null && data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				receptionistRepository.deleteById(id);
				return new  ResultResponse<String>(true,1, "Process Successfully");
            } else {
            	return new  ResultResponse<String>(true,2, "Process Failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new  ResultResponse<String>(true,2, e.getMessage());
        }
	}

	@Override
	public  ResultResponse<String> createReceptionist(ReceptionistDto receptionistDto, String emailCreatedBy) {
		try {
			receptionistDto.setEmail(receptionistDto.getEmail().toLowerCase());

			if(systemRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(receptionistRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(reservationAgentRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(guestRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			if(systemRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<String>(true,2, "Email already");
			}
			var reservationAgent = reservationAgentRepository.findByEmail(emailCreatedBy);
			if(receptionistRepository.findByReservationAgentEmail(emailCreatedBy).size()>= reservationAgent.getTotalRestaurant()) {
				return new  ResultResponse<String>(true,2, "The creation limit has been reached");
			}
			
			var data = ReceptionistMapper.mapToModel(receptionistDto);
			data.setReservationAgent(reservationAgentRepository.findByEmail(emailCreatedBy));
			var password = GenerateCode.GeneratePassword(12);
			var hashPassword = cryptPasswordEncoder.encode(password);
			data.setReservationAgent(reservationAgentRepository.findByEmail(emailCreatedBy));
			data.setPassword(hashPassword);
			String email = environment.getProperty("spring.mail.username");
			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), password);
			if (mailService.send(email, data.getEmail(), "Account for you", content)) {
				
			} else {
				return new  ResultResponse<String>(true,2, "Send Email Fail");
			}
			
			if(receptionistRepository.save(data)!=null) {
				return new  ResultResponse<String>(true,1, "Process Successfully");
			}else {
				return new  ResultResponse<String>(true,2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<String>(true,2, e.getMessage());
		}
	}

	@Override
	public boolean changeStatus(String id,String emailCreatedBy) {
		try {
			var data = receptionistRepository.findById(id).get();
			if(data== null) {
				return false;
			}
			if(data!=null && data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
			data.setStatus(!data.isStatus());
			if(receptionistRepository.save(data)!=null) return true;
			}
			return false;
		}catch (Exception e) {
			return false;
		}
		
	}


}
