package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.*;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.mappers.*;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.models.Restaurant;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.servicies.IDinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DinnerTableService implements IDinnerTableService {

    @Autowired
    private DinnerTableRepository dinnerTableRepository;
    @Autowired
    private ReservationAgentRepository agentRepository;

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
            return new ResultResponse<DinnerTableDto>(true, new DinnerTableDto(
                    saved.getId(),
                    dinnerTableDto.getQuantity(),
                    dinnerTableDto.getCurrentQuantity(),
                    dinnerTableDto.getStatus(),
                    dinnerTableDto.getDinnerTableTypeDto(),
                    dinnerTableDto.getDinnerTableTypeList(),
                    dinnerTableDto.getDinnerTableTypeDtoId(),
                    dinnerTableDto.getRestaurantDtoId(),
                    dinnerTableDto.getRestaurantDto(),
                    dinnerTableDto.getRestaurantList(),
                    dinnerTableDto.getImagesDto(),
                    dinnerTableDto.getImageDto()));

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
            var saved = dinnerTableRepository.save(data);
            if(saved!=null) {
                return	new ResultResponse<DinnerTableDto>(true,new DinnerTableDto(
                        saved.getId(),
                        dinnerTableDto.getQuantity(),
                        dinnerTableDto.getCurrentQuantity(),
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

    @Override
    public List<DinnerTableDto> getDinnerTablesByType(DinnerTableTypeDto dinnerTableTypeDto) {
        List<DinnerTableDto> dinnerTables = dinnerTableRepository.findByDinnerTableType(DinnerTableTypeMapper.mapToModel(dinnerTableTypeDto))
                .stream()
                .map(DinnerTableMapper::mapToDto)
                .collect(Collectors.toList());
        return dinnerTables;
    }

    @Override
    public List<DinnerTableDto> getAllDinnerTablesForRestaurant(String restaurantId) {
        return dinnerTableRepository.findByRestaurant_Id(restaurantId).stream().map(i -> DinnerTableMapper.mapToDto(i)).collect(Collectors.toList());
    }


}
