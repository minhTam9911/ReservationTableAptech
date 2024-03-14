package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.dtos.ResultResponse;

public interface ICategoryRestaurantService {

	CategoryRestaurantDto getById(Integer id);
	List<CategoryRestaurantDto> getList();
	ResultResponse<String> insert(CategoryRestaurantDto categoryRestaurantDto);
	ResultResponse<String> update(Integer id,CategoryRestaurantDto categoryRestaurantDto);
	ResultResponse<String> delete(Integer id);
}
