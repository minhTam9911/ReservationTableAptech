package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.mappers.RoleMapper;
import com.bookingtable.models.RateType;
import com.bookingtable.repositories.RateTypeRepository;
import com.bookingtable.mappers.RateTypeMapper;
import com.bookingtable.repositories.RoleRepository;
import com.bookingtable.servicies.IRateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateTypeService implements IRateTypeService{

    @Autowired
    private RateTypeRepository rateTypeRepository;
    @Override
    public List<RateTypeDto> getAllRateTypes() {

        return rateTypeRepository.findAll().stream().map(i-> RateTypeMapper.mapToDto(i)).collect(Collectors.toList());
    }

    @Override
    public RateTypeDto getRateTypeById(Integer id) {
        return RateTypeMapper.mapToDto(rateTypeRepository.findById(id).get());

    }

    @Override
    public ResultResponse<RateTypeDto> createRateType(RateTypeDto rateTypeDto) {
        try {
            if(rateTypeRepository.save(RateTypeMapper.mapToModel(rateTypeDto))!=null) {
                return new ResultResponse<RateTypeDto>(true,new RateTypeDto());
            }else {
                return new ResultResponse<RateTypeDto>(false,new RateTypeDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<RateTypeDto>(false,new RateTypeDto());
        }
    }

    @Override
    public ResultResponse<RateTypeDto> updateRateType(Integer id, RateTypeDto rateTypeDto) {
        try {
            var data = rateTypeRepository.findById(id).get();
            data.setType(rateTypeDto.getType());
            data.setDescription(rateTypeDto.getDescription());

            if(rateTypeRepository.save(data)!=null) {
                return	new ResultResponse<RateTypeDto>(true,new RateTypeDto());
            }else {
                return new ResultResponse<RateTypeDto>(false,new RateTypeDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<RateTypeDto>(false,new RateTypeDto());
        }

    }

    @Override
    public ResultResponse<RateTypeDto> deleteRateType(Integer id) {
        try {
            if(rateTypeRepository.findById(id)!=null) {
                rateTypeRepository.deleteById(id);
                return	new ResultResponse<RateTypeDto>(true,new RateTypeDto());
            }else {
                return	new ResultResponse<RateTypeDto>(false,new RateTypeDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return	new ResultResponse<RateTypeDto>(false,new RateTypeDto());
        }
    }
}
