package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.mappers.ReceptionistMapper;
import com.bookingtable.mappers.ReservationAgentMapper;
import com.bookingtable.repositories.GuestRepository;
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
	private GuestRepository guestRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private IMailService mailService;
	@Autowired
	private Environment environment;
	@Override
	public List<ReceptionistDto> getAllReceptionist() {

		return receptionistRepository.findAll().stream().map(i->ReceptionistMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ReceptionistDto getReceptionistById(String id) {
		return ReceptionistMapper.mapToDto(receptionistRepository.findById(id).get());
	}



	@Override
	public  ResultResponse<ReceptionistDto> updateReceptionist(String id, ReceptionistDto receptionistDto) {
		try {

			if(receptionistRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			if(reservationAgentRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			//if(guestRepository.existEmail(reservationAgentDto.getEmail().toLowerCase(),id)!=null) {
				//return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			//}
			if(systemRepository.existEmail(receptionistDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			var data = receptionistRepository.findById(id).get();
			data.setFullname(receptionistDto.getFullname());
			data.setAddress(receptionistDto.getAddress());
			data.setEmail(receptionistDto.getEmail());
			data.setPhoneNumber(receptionistDto.getPhoneNumber());
			data.setGender(receptionistDto.isGender());
			data.setUpdated(LocalDate.now());
			if(receptionistRepository.save(data)!=null) {
				return new  ResultResponse<ReceptionistDto>(true, new ReceptionistDto());
			}else {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto(e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<ReceptionistDto> deleteReceptionist(String id) {
		try {

			if(receptionistRepository.findById(id)!=null) {
				receptionistRepository.deleteById(id);
                return new ResultResponse<ReceptionistDto>(true, new ReceptionistDto());
            } else {
                return new ResultResponse<ReceptionistDto>(false, new ReceptionistDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<ReceptionistDto>(false, new ReceptionistDto(e.getMessage()));
        }
	}

	@Override
	public  ResultResponse<ReceptionistDto> createReceptionist(ReceptionistDto receptionistDto) {
		try {
			receptionistDto.setEmail(receptionistDto.getEmail().toLowerCase());

			if(systemRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			if(receptionistRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			//if(guestRepository.findByEmail(reservationAgentDto.getEmail().toLowerCase())!=null) {
			//	return new  ResultResponse<ReservationAgentDto>(false, new ReservationAgentDto("Email already"));
			//}
			if(systemRepository.findByEmail(receptionistDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
			}
			var data = ReceptionistMapper.mapToModel(receptionistDto);
			data.setPassword(GenerateCode.GeneratePassword(12));
			String email = environment.getProperty("spring.mail.username");
//			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), data.getPassword());
			if (mailService.send(email, data.getEmail(), "Account for you", "")) {
				
			} else {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Send Email Fail"));
			}
			
			if(receptionistRepository.save(data)!=null) {
				return new  ResultResponse<ReceptionistDto>(true, new ReceptionistDto());
			}else {
				return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<ReceptionistDto>(false, new ReceptionistDto("Email already"));
		}
	}

	@Override
	public boolean changeStatus(String id) {
		try {
			var data = receptionistRepository.findById(id).get();
			if(data== null) {
				return false;
			}
			data.setStatus(!data.isStatus());
			if(receptionistRepository.save(data)!=null) return true;
			return false;
		}catch (Exception e) {
			return false;
		}
		
	}


}
