package com.bookingtable.servicies;

import com.bookingtable.dtos.GuestDto;
import com.bookingtable.dtos.PermissionDto;

import java.util.List;

public interface IPermissionService {
    public List<PermissionDto> getAllPermission();

    public PermissionDto getPermissionById(Integer id);

    public boolean createPermission(PermissionDto permissionDto) ;
    public boolean updatePermission(Integer id, PermissionDto permissionDto);

    public boolean deletePermission(Integer id);
}
