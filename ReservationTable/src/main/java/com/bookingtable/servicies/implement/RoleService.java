package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.repositories.RoleRepository;
import com.bookingtable.servicies.IRoleService;
@Service
public class RoleService implements IRoleService {

	private RoleRepository roleRepository;
	@Override
	public List<RoleDto> getAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDto getRoleById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createRole(RoleDto roleDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRole(Integer id, RoleDto roleDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRole(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
