package com.bookingtable.servicies.implement;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.CategoryRestaurantMapper;
import com.bookingtable.repositories.CategoryRestaurantRepository;
import com.bookingtable.servicies.ICategoryRestaurantService;

@Service
public class CategoryRestaurantService implements ICategoryRestaurantService {

	@Autowired
	private CategoryRestaurantRepository categoryRestaurantRepository;

	@Override
	public CategoryRestaurantDto getById(Integer id) {
		return CategoryRestaurantMapper.mapToDto(categoryRestaurantRepository.findById(id).get());
	}

	@Override
	public List<CategoryRestaurantDto> getList() {
		
		return categoryRestaurantRepository.findAll().stream().map(i->CategoryRestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ResultResponse<String> insert(CategoryRestaurantDto categoryRestaurantDto) {
		if(categoryRestaurantRepository.findByName(toUpperCaseFirstLetter(categoryRestaurantDto.getName())).size()>0) {
			return new ResultResponse<String>(true,2,"Name Already");
		}
		categoryRestaurantDto.setName(toUpperCaseFirstLetter(categoryRestaurantDto.getName()));
		if(categoryRestaurantRepository.save(CategoryRestaurantMapper.mapToModel(categoryRestaurantDto))!=null) {
			return new ResultResponse<String>(true,1,"Process Successfully");
		}else {
			return new ResultResponse<String>(true,2,"Process Failure");
		}
	}

	@Override
	public ResultResponse<String> update(Integer id, CategoryRestaurantDto categoryRestaurantDto) {
		if(categoryRestaurantRepository.existName(toUpperCaseFirstLetter(categoryRestaurantDto.getName()),id).size() >0) {
			return new ResultResponse<String>(true,2,"Name Already");
		}
		categoryRestaurantDto.setName(toUpperCaseFirstLetter(categoryRestaurantDto.getName()));
		categoryRestaurantDto.setId(id);
		if(categoryRestaurantRepository.save(CategoryRestaurantMapper.mapToModel(categoryRestaurantDto))!=null) {
			return new ResultResponse<String>(true,1,"Process Successfully");
		}else {
			return new ResultResponse<String>(false,2,"Process Failure");
		}
	}

	@Override
	public ResultResponse<String> delete(Integer id) {
		if(categoryRestaurantRepository.findById(id) == null) {
			return new ResultResponse<String>(true,2,"Id Does not exist");
		}
		categoryRestaurantRepository.deleteById(id);
		return new ResultResponse<String>(true,1,"Process Successfully");
	}
	
	private  String toUpperCaseFirstLetter(String str) {
	    String[] words = str.split(" ");
	    StringBuilder sb = new StringBuilder();
	    for (String word : words) {
	        sb.append(word.charAt(0)).append(word.substring(1)).append(" ");
	    }
	    return sb.toString().trim();
	}
	
}
