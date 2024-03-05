package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.mappers.ReservationAgentMapper;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IMailService;
import com.bookingtable.servicies.IReservationAgentService;
@Service
public class ReservationAgentService implements IReservationAgentService {

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
	@Override
	public List<ReservationAgentDto> getAllReservationAgents() {

		return reservationAgentRepository.findAll().stream().map(i->ReservationAgentMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ReservationAgentDto getReservationAgentById(String id) {
		return ReservationAgentMapper.mapToDto(reservationAgentRepository.findById(id).get());
	}



	@Override
	public  ResultResponse<ReservationAgentDto> updateReservationAgent(String id, ReservationAgentDto reservationAgentDto) {
		try {

			if(receptionistRepository.existEmail(reservationAgentDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			if(reservationAgentRepository.existEmail(reservationAgentDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			//if(guestRepository.existEmail(reservationAgentDto.getEmail().toLowerCase(),id)!=null) {
				//return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			//}
			if(systemRepository.existEmail(reservationAgentDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			var data = reservationAgentRepository.findById(id).get();
			data.setFullName(reservationAgentDto.getFullName());
			data.setAddress(reservationAgentDto.getAddress());
			data.setCity(reservationAgentDto.getCity());
			data.setDistrict(reservationAgentDto.getDistrict());
			data.setWard(reservationAgentDto.getWard());
			data.setEmail(reservationAgentDto.getEmail());
			data.setCellularPhoneNumber(reservationAgentDto.getCellularPhoneNumber());
			data.setHomePhoneNumber(reservationAgentDto.getHomePhoneNumber());
			data.setTotalRestaurant(reservationAgentDto.getTotalRestaurant());
			data.setUpdated(LocalDate.now());
			if(reservationAgentRepository.save(data)!=null) {
				return new  ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}else {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto(e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<ReservationAgentDto> deleteReservationAgent(String id) {
		try {

			if(reservationAgentRepository.findById(id)!=null) {
				reservationAgentRepository.deleteById(id);
                return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
            } else {
                return new ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto(e.getMessage()));
        }
	}

	@Override
	public  ResultResponse<ReservationAgentDto> createReservationAgent(ReservationAgentDto reservationAgentDto) {
		try {
			reservationAgentDto.setEmail(reservationAgentDto.getEmail().toLowerCase());

			if(systemRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			if(receptionistRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			//if(guestRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
			//	return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			//}
			if(systemRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
			var data = ReservationAgentMapper.mapToModel(reservationAgentDto);
			data.setPassword(GenerateCode.GeneratePassword(12));
			String email = environment.getProperty("spring.mail.username");
//			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), data.getPassword());
			if (mailService.send(email, data.getEmail(), "Account for you", "")) {
				
			} else {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Send Email Fail"));
			}
			
			if(reservationAgentRepository.save(data)!=null) {
				return new  ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}else {
				return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
		}
	}

	@Override
	public boolean changeStatus(String id) {
		try {
			var data = reservationAgentRepository.findById(id).get();
			if(data== null) {
				return false;
			}
			data.setStatus(!data.isStatus());
			if(reservationAgentRepository.save(data)!=null) return true;
			return false;
		}catch (Exception e) {
			return false;
		}
		
	}

}
