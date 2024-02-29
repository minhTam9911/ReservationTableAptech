package com.bookingtable.servicies.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.mappers.PermissionMapper;
import com.bookingtable.mappers.RoleMapper;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.repositories.RoleRepository;
import com.bookingtable.servicies.IRoleService;
@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Override
	public List<RoleDto> getAllRoles() {
		
		return roleRepository.findAll().stream().map(i->RoleMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public RoleDto getRoleById(Integer id) {
		return RoleMapper.mapToDto(roleRepository.findById(id).get());

	}

	@Override
	public boolean createRole(RoleDto roleDto) {
		try {
			
			if(roleRepository.save(RoleMapper.mapToModel(roleDto))!=null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateRole(Integer id, RoleDto roleDto) {
		try {
			var data = roleRepository.findById(id).get();
			data.setName(roleDto.getName());
			data.setPermissions(roleDto.getPermissionsDto().stream().map(i->PermissionMapper.mapToModel(i)).collect(Collectors.toList()));
			if(roleRepository.save(data)!=null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteRole(Integer id) {
		try {
			if(roleRepository.findById(id)!=null) {
				roleRepository.deleteById(id);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
