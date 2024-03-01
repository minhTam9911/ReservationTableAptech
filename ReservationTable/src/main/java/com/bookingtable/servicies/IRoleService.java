package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;

public interface IRoleService {
	public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Integer id);

    public ResultResponse<RoleDto> createRole(RoleDto roleDto) ;
    public ResultResponse<RoleDto> updateRole(Integer id, RoleDto roleDto);

    public ResultResponse<RoleDto> deleteRole(Integer id);
}
