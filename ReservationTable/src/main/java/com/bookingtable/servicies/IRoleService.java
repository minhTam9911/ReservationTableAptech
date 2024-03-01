package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;

public interface IRoleService {
	public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Integer id);

    public ResultResponse createRole(RoleDto roleDto) ;
    public ResultResponse updateRole(Integer id, RoleDto roleDto);

    public ResultResponse deleteRole(Integer id);
}
