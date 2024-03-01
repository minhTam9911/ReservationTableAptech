package com.bookingtable.mappers;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.models.Image;
import java.util.stream.Collectors;

public class ImageMapper {
    public static Image mapToModel(ImageDto imageDto) {
        return Image.builder()
                .id(imageDto.getId())
                .path(imageDto.getPath())
                
                .build();
    }
    public static ImageDto mapToDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .path(image.getPath())
                .restaurantDto(RestaurantMapper.mapToDto(image.getRestaurant()))
                .dinnerTableDto(DinnerTableMapper.mapToDto(image.getDinnerTable()))
                .build();
    }
}
