package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.RoleDto;

public interface IRoleService {
	public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Integer id);

    public boolean createRole(RoleDto roleDto) ;
    public boolean updateRole(Integer id, RoleDto roleDto);

    public boolean deleteRole(Integer id);
}
