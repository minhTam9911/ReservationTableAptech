package com.bookingtable.mappers;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.models.Image;
public class ImageMapper {
    public static Image mapToModel(ImageDto imageDto) {
        return Image.builder()
                .id(imageDto.getId())
                .path(imageDto.getPath())
                .dinnerTable(imageDto.getDinnerTableDto() == null? null : DinnerTableMapper.mapToModel(imageDto.getDinnerTableDto()))
                .restaurant(imageDto.getRestaurantDto() == null? null: RestaurantMapper.mapToModel(imageDto.getRestaurantDto()))
                .build();
    }
    public static ImageDto mapToDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .path(image.getPath())
                .restaurantDto(image.getRestaurant() == null? null:RestaurantMapper.mapToDto(image.getRestaurant()))
                .dinnerTableDto(image.getDinnerTable() == null? null : DinnerTableMapper.mapToDto(image.getDinnerTable()))
                .build();
    }
}
