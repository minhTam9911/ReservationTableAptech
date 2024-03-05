package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.mappers.*;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.servicies.IDinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DinnerTableService implements IDinnerTableService {

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Override
    public List<DinnerTableDto> getAllDinnerTables() {
        return dinnerTableRepository.findAll().stream().map(i-> DinnerTableMapper.mapToDto(i)).collect(Collectors.toList());

    }
    @Override
    public DinnerTableDto getDinnerTableById(Integer id) {
        Optional<DinnerTable> optionalDinnerTable = dinnerTableRepository.findById(id);
        if (optionalDinnerTable.isPresent()) {
            return DinnerTableMapper.mapToDto(optionalDinnerTable.get());
        }
        return null;
    }

    @Override
    public ResultResponse<DinnerTableDto> createDinnerTable(DinnerTableDto dinnerTableDto) {
        var saved =dinnerTableRepository.save(DinnerTableMapper.mapToModel(dinnerTableDto));
        if(saved!=null) {
            return new ResultResponse<>(true, new DinnerTableDto(
                    saved.getId(),
                    dinnerTableDto.getQuantity(),
                    dinnerTableDto.getStatus(),
                    dinnerTableDto.getDinnerTableTypeDto(),
                    dinnerTableDto.getDinnerTableTypeList(),
                    dinnerTableDto.getDinnerTableTypeDtoId(),
                    dinnerTableDto.getRestaurantDtoId(),
                    dinnerTableDto.getRestaurantDto(),
                    dinnerTableDto.getRestaurantList(),
                    dinnerTableDto.getImagesDto(),
                    dinnerTableDto.getImageDto()
            ));

        }else {
            return new  ResultResponse<DinnerTableDto>(false, new DinnerTableDto());
        }
    }


    @Override
    public  ResultResponse<DinnerTableDto> updateDinnerTable(Integer id,DinnerTableDto dinnerTableDto) {
        try {
            var data = dinnerTableRepository.findById(id).get();
            data.setId(dinnerTableDto.getId());

            data.setQuantity(dinnerTableDto.getQuantity());
            data.setStatus(dinnerTableDto.getStatus());
            data.setRestaurant(RestaurantMapper.mapToModel(dinnerTableDto.getRestaurantDto()));
            data.setDinnerTableType(DinnerTableTypeMapper.mapToModel(dinnerTableDto.getDinnerTableTypeDto()));
            if(dinnerTableRepository.save(data)!=null) {
                return	new ResultResponse<DinnerTableDto>(true,new DinnerTableDto());
            }else {
                return new ResultResponse<DinnerTableDto>(false,new DinnerTableDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<DinnerTableDto>(false,new DinnerTableDto());
        }
    }

    @Override
    public  ResultResponse<DinnerTableDto> deleteDinnerTable(Integer id) {
        try {
            if(dinnerTableRepository.findById(id)!=null) {
                dinnerTableRepository.deleteById(id);
                return	new ResultResponse<DinnerTableDto>(true,new DinnerTableDto());
            }else {
                return	new ResultResponse<DinnerTableDto>(false,new DinnerTableDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return	new ResultResponse<DinnerTableDto>(false,new DinnerTableDto());
        }

    }
}
