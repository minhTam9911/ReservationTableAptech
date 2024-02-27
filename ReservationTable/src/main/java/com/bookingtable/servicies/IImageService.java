package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;

import java.util.List;

public interface IImageService {
    public List<ImageDto> getAllImages();

    public ImageDto getImageById(Integer id);

    public boolean createImage(ImageDto imageDto) ;
    public boolean updateImage(Integer id, ImageDto imageDto);

    public boolean deleteImage(Integer id);
}
