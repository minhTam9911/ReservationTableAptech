package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.dtos.ResultResponse;

public interface ICategoryRestaurantService {

	CategoryRestaurantDto getById(Integer id);
	List<CategoryRestaurantDto> getList();
	ResultResponse<CategoryRestaurantDto> insert(CategoryRestaurantDto categoryRestaurantDto);
	ResultResponse<CategoryRestaurantDto> update(Integer id,CategoryRestaurantDto categoryRestaurantDto);
	ResultResponse<CategoryRestaurantDto> delete(Integer id);
}
