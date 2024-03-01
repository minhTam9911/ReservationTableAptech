package com.bookingtable.servicies.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
//import com.bookingtable.mappers.PermissionMapper;
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
	public ResultResponse<RoleDto> createRole(RoleDto roleDto) {
		try {
			if(roleRepository.findByName(roleDto.getName()).size()>0) {
				return new ResultResponse<RoleDto>(false,new RoleDto(0,"Name already"));
			}
			if(roleRepository.save(RoleMapper.mapToModel(roleDto))!=null) {
				return new ResultResponse<RoleDto>(true,new RoleDto());
			}else {
				return new ResultResponse<RoleDto>(false,new RoleDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<RoleDto>(false,new RoleDto(0,e.getMessage()));
		}
	}

	@Override
	public ResultResponse<RoleDto> updateRole(Integer id, RoleDto roleDto) {
		try {
			var data = roleRepository.findById(id).get();
			if(data.getName() != roleDto.getName()) {
				if(roleRepository.existName(roleDto.getName(), id).size()>0) {
				return	new ResultResponse<RoleDto>(false,new RoleDto(0,"Name already"));
				}
			}
			data.setName(roleDto.getName());
				if(roleRepository.save(data)!=null) {
				return	new ResultResponse<RoleDto>(true,new RoleDto());
			}else {
				return new ResultResponse<RoleDto>(false,new RoleDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<RoleDto>(false,new RoleDto(0,e.getMessage()));
		}
		
	}

	@Override
	public ResultResponse<RoleDto> deleteRole(Integer id) {
		try {
			if(roleRepository.findById(id)!=null) {
				roleRepository.deleteById(id);
				return	new ResultResponse<RoleDto>(true,new RoleDto());
			}else {
				return	new ResultResponse<RoleDto>(false,new RoleDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return	new ResultResponse<RoleDto>(false,new RoleDto(0,"Name already"));
		}
	}

}
