package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.mappers.DinnerTableMapper;
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
        return dinnerTableRepository.findAll().stream()
                .map(DinnerTableMapper::mapToDto)
                .collect(Collectors.toList());
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
    public boolean createDinnerTable(DinnerTableDto dinnerTableDto) {
        DinnerTable dinnerTable = DinnerTableMapper.mapToModel(dinnerTableDto);
        DinnerTable savedDinnerTable = dinnerTableRepository.save(dinnerTable);
        return DinnerTableMapper.mapToDto(savedDinnerTable)!=null;
    }

    @Override
    public boolean updateDinnerTable(Integer id, DinnerTableDto dinnerTableDto) {
        Optional<DinnerTable> optionalDinnerTable = dinnerTableRepository.findById(id);
        if (optionalDinnerTable.isPresent()) {
            DinnerTable dinnerTable = optionalDinnerTable.get();
            DinnerTable updatedDinnerTable = dinnerTableRepository.save(dinnerTable);
            return DinnerTableMapper.mapToDto(updatedDinnerTable)!=null;
        }
        return false;
    }

    @Override
    public boolean deleteDinnerTable(Integer id) {
        if (dinnerTableRepository.existsById(id)) {
            dinnerTableRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
