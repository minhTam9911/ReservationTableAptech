package com.bookingtable.mappers;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.models.Image;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======

>>>>>>> e0a44d4fbac169b2a4c7f108cd5eb9007b3bdf15

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
