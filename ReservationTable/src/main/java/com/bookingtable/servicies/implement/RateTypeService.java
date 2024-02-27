package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.models.RateType;
import com.bookingtable.repositories.RateTypeRepository;
import com.bookingtable.mappers.RateTypeMapper;
import com.bookingtable.servicies.IRateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateTypeService implements IRateTypeService{

    @Autowired
    private RateTypeRepository rateTypeRepository;

    public List<RateTypeDto> getAllRateTypes() {
        return rateTypeRepository.findAll().stream()
                .map(RateTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public RateTypeDto getRateTypeById(Integer id) {
        RateType rateType = rateTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RateType not found with id: " + id));
        return RateTypeMapper.mapToDto(rateType);
    }

    public boolean createRateType(RateTypeDto rateDto) {
        RateType rateType = RateTypeMapper.mapToModel(rateDto);
        return rateTypeRepository.save(rateType)!=null;
        
    }

    public boolean updateRateType(Integer id, RateTypeDto rateDto) {
        RateType existingRateType = rateTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RateType not found with id: " + id));

        RateType updatedRateType = RateTypeMapper.mapToModel(rateDto);
        updatedRateType.setId(existingRateType.getId()); // Ensure the ID remains the same

        return rateTypeRepository.save(updatedRateType) !=null;
        
    }

    public boolean deleteRateType(Integer id) {
        if (rateTypeRepository.existsById(id)) {
            rateTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
