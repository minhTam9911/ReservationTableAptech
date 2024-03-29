package com.bookingtable.mappers;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.models.Collection;
import com.bookingtable.models.Customer;

public class CollectionMapper {

    public static Collection mapToModel(CollectionDto collectionDto) {
        return Collection.builder()
                .id(collectionDto.getId())
                .customer(CustomerMapper.mapToModel(collectionDto.getCustomer()))
                .restaurant(RestaurantMapper.mapToModel(collectionDto.getRestaurant()))
                .status(collectionDto.isStatus()).build();
    }

    public static CollectionDto mapToDto(Collection collection) {
        CollectionDto dto = new CollectionDto();
        if(collection != null) {
            dto.setId(collection.getId());
            dto.setCustomer(CustomerMapper.mapToDto(collection.getCustomer()));
            dto.setRestaurant(RestaurantMapper.mapToDto(collection.getRestaurant()));
            dto.setStatus(collection.isStatus());
        }
        return dto;
    }

}
