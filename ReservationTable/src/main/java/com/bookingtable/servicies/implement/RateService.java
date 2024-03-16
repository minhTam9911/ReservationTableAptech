package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RateDto;
import com.bookingtable.models.Rate;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.mappers.RateMapper;
import com.bookingtable.servicies.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService implements IRateService {

    @Autowired
    private RateRepository rateRepository;

    public List<RateDto> getAllRate() {
        return rateRepository.findAll().stream()
                .map(RateMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RateDto> getRateByReservationId(String id) {
        return rateRepository.findByReservationId(id).stream()
                .map(RateMapper::mapToDto)
                .collect(Collectors.toList());    }

    public RateDto getRateById(Integer id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found with id: " + id));
        return RateMapper.mapToDto(rate);
    }

    public boolean createRate(RateDto rateDto) {
        Rate rate = RateMapper.mapToModel(rateDto);
        Rate savedRate = rateRepository.save(rate);
        return RateMapper.mapToDto(savedRate)!=null;
    }

    public boolean updateRate(Integer id, RateDto rateDto) {
        Rate existingRate = rateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found with id: " + id));

        Rate updatedRate = RateMapper.mapToModel(rateDto);
        updatedRate.setId(existingRate.getId()); // Ensure the ID remains the same

        Rate savedRate = rateRepository.save(updatedRate);
        return RateMapper.mapToDto(savedRate)!=null;
    }

    public boolean deleteRate(Integer id) {
        if (rateRepository.existsById(id)) {
            rateRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
