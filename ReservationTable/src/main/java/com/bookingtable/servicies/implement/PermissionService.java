package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.PermissionDto;
import com.bookingtable.models.Permission;
import com.bookingtable.repositories.PermissionRepository;
import com.bookingtable.mappers.PermissionMapper;
import com.bookingtable.servicies.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getAllPermission() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionDto getPermissionById(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
        return PermissionMapper.mapToDto(permission);
    }

    @Override
    public boolean createPermission(PermissionDto permissionDto) {
        Permission permission = PermissionMapper.mapToModel(permissionDto);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.mapToDto(savedPermission)!=null;
    }

    @Override
    public boolean updatePermission(Integer id, PermissionDto permissionDto) {
        Permission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));

        Permission updatedPermission = PermissionMapper.mapToModel(permissionDto);
        updatedPermission.setId(existingPermission.getId()); // Ensure the ID remains the same

        Permission savedPermission = permissionRepository.save(updatedPermission);
        return PermissionMapper.mapToDto(savedPermission)!=null;
    }

    @Override
    public boolean deletePermission(Integer id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}