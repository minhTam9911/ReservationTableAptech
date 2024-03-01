package com.bookingtable.servicies.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
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
	public ResultResponse createRole(RoleDto roleDto) {
		try {
			if(roleRepository.findByName(roleDto.getName()).size()>0) {
				return new ResultResponse(false,"Name already");
			}
			if(roleRepository.save(RoleMapper.mapToModel(roleDto))!=null) {
				return new ResultResponse(true,"Create Sucessfull");
			}else {
				return new ResultResponse(false,"Create Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse(false,e.getMessage());
		}
	}

	@Override
	public ResultResponse updateRole(Integer id, RoleDto roleDto) {
		try {
			var data = roleRepository.findById(id).get();
			if(data.getName() != roleDto.getName()) {
				if(roleRepository.existName(roleDto.getName(), id).size()>0) {
					return new ResultResponse(false,"Name already");
				}
			}
			data.setName(roleDto.getName());
			data.setPermissions(roleDto.getPermissionsDto().stream().map(i->PermissionMapper.mapToModel(i)).collect(Collectors.toList()));
			if(roleRepository.save(data)!=null) {
				return new ResultResponse(true,"Update Successful");
			}else {
				return new ResultResponse(false,"Update Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse(false,e.getMessage());
		}
	}

	@Override
	public ResultResponse deleteRole(Integer id) {
		try {
			if(roleRepository.findById(id)!=null) {
				roleRepository.deleteById(id);
				return new ResultResponse(true,"Delete Successful");
			}else {
				return new ResultResponse(false,"Delete Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse(false,e.getMessage());
		}
	}

}
