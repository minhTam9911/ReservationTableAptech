package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.mappers.DinnerTableMapper;
import com.bookingtable.mappers.RoleMapper;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.repositories.DinnerTableTypeRepository;
import com.bookingtable.mappers.DinnerTableTypeMapper;
import com.bookingtable.servicies.IDinnerTableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DinnerTableTypeService implements IDinnerTableTypeService {

    @Autowired
    private DinnerTableTypeRepository dinnerTableTypeRepository;

    @Override
    public List<DinnerTableTypeDto> getAllDinnerTablesType() {
        return dinnerTableTypeRepository.findAll().stream()
                .map(DinnerTableTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DinnerTableTypeDto getDinnerTableTypeById(Integer id) {
        Optional<DinnerTableType> optionalDinnerTable = dinnerTableTypeRepository.findById(id);
        if (optionalDinnerTable.isPresent()) {
            return DinnerTableTypeMapper.mapToDto(optionalDinnerTable.get());
        }
        return null;

    }

    @Override
    public ResultResponse<DinnerTableTypeDto> createDinnerTableType(DinnerTableTypeDto dinnerTableTypeDto) {
        DinnerTableType dinnerTableType = DinnerTableTypeMapper.mapToModel(dinnerTableTypeDto);
        try {
            if (dinnerTableTypeRepository.save(dinnerTableType) != null) {
                return new ResultResponse<DinnerTableTypeDto>(true, new DinnerTableTypeDto());
            } else {
                return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto(0, 0, "", "", new HashSet<>()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto(0, 0, "", "", new HashSet<>()));
        }

    }

    public ResultResponse<DinnerTableTypeDto> updateDinnerTableType(Integer id, DinnerTableTypeDto dinnerTableTypeDto) {
        try {
            DinnerTableType existingType = dinnerTableTypeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Table type not found with id: " + id));

            DinnerTableType updatedType = DinnerTableTypeMapper.mapToModel(dinnerTableTypeDto);
            updatedType.setId(existingType.getId()); // Ensure the ID remains the same

            DinnerTableType savedType = dinnerTableTypeRepository.save(updatedType);

            if (DinnerTableTypeMapper.mapToDto(savedType) != null) {
                return new ResultResponse<DinnerTableTypeDto>(true, new DinnerTableTypeDto());
            } else {
                return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto());
        }
    }

    public ResultResponse<DinnerTableTypeDto> deleteDinnerTableType(Integer id) {
        try {
            if (dinnerTableTypeRepository.findById(id) != null) {
                dinnerTableTypeRepository.deleteById(id);
                return new ResultResponse<DinnerTableTypeDto>(true, new DinnerTableTypeDto());
            } else {
                return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<DinnerTableTypeDto>(false, new DinnerTableTypeDto());
        }

    }
}
