package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.mappers.DinnerTableMapper;
import com.bookingtable.mappers.RoleMapper;
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
    public boolean createDinnerTable(DinnerTableDto dinnerTableDto) {
        DinnerTable dinnerTable = DinnerTableMapper.mapToModel(dinnerTableDto);
        DinnerTable savedDinnerTable = dinnerTableRepository.save(dinnerTable);
        return savedDinnerTable!=null;
    }

    @Override
    public boolean updateDinnerTable(DinnerTableDto dinnerTableDto) {
         return dinnerTableRepository.save(DinnerTableMapper.mapToModel(dinnerTableDto))!=null;
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
