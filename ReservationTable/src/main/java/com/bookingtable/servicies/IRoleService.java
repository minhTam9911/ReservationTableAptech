package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;

public interface IRoleService {
	public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Integer id);

    public ResultResponse<String> createRole(RoleDto roleDto) ;
    public ResultResponse<String> updateRole(Integer id, RoleDto roleDto);

    public ResultResponse<String> deleteRole(Integer id);
}
