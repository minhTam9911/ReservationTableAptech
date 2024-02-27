package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.repositories.DinnerTableTypeRepository;
import com.bookingtable.mappers.DinnerTableTypeMapper;
import com.bookingtable.servicies.IDinnerTableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
        DinnerTableType dinnerTableType = dinnerTableTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table type not found with id: " + id));
        return DinnerTableTypeMapper.mapToDto(dinnerTableType);
    }
    @Override
    public boolean createDinnerTableType(DinnerTableTypeDto dinnerTableTypeDto) {
        DinnerTableType dinnerTableType = DinnerTableTypeMapper.mapToModel(dinnerTableTypeDto);
        DinnerTableType savedDinnerTableType = dinnerTableTypeRepository.save(dinnerTableType);
        return DinnerTableTypeMapper.mapToDto(savedDinnerTableType)!=null;
    }
    public boolean updateDinnerTableType(Integer id, DinnerTableTypeDto dinnerTableTypeDto) {
        DinnerTableType existingType = dinnerTableTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table type not found with id: " + id));

        DinnerTableType updatedType = DinnerTableTypeMapper.mapToModel(dinnerTableTypeDto);
        updatedType.setId(existingType.getId()); // Ensure the ID remains the same

        DinnerTableType savedType = dinnerTableTypeRepository.save(updatedType);
        return DinnerTableTypeMapper.mapToDto(savedType)!=null;
    }
    public boolean deleteDinnerTableType(Integer id) {
        if (dinnerTableTypeRepository.existsById(id)) {
            dinnerTableTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
